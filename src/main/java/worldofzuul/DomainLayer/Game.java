package worldofzuul.DomainLayer;

import worldofzuul.DataLayer.*;
import worldofzuul.CLILayer.CommandWord;
import worldofzuul.DomainLayer.Interfaces.IGame;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IPlayer;
import worldofzuul.DomainLayer.Interfaces.IRoom;
import worldofzuul.PresentationLayer.MainGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.reverse;


/**
 * The main class of the game
 * Processes the commands from the parser and takes care of all writing to the screen
 * This keeps track of the current room, the player and a list of finished games intended
 * for calculating a total score across multiple games.
 * There is a method for each command the user can enter
 * They are all called from the processCommand function
 */

public class Game implements IGame {

    private final Player player;
    private final ArrayList<GameResult> finishedGames;
    private final ISaveGame saveGame;
    private final ArrayList<Room> rooms;
    private boolean isCo2Bad = false;
    private boolean isNotHappy = false;


    public Game() {
        rooms = ContentGenerator.getRooms();
        player = new Player(ContentGenerator.getPlayerTypeByName(ContentGenerator.RANDOM_TYPE_NAME),
                this.rooms.get(0)); //Set's starting room to the first room (outside).

        finishedGames = new ArrayList<>();
        saveGame = new SaveFile("./saveFile.json");
        loadGame();
    }

    void loadGame() {
        finishedGames.clear();
        try {
            ArrayList<GameResultData> loadedData = saveGame.load();
            for (GameResultData resultData : loadedData) {
                GameResult result = new GameResult(resultData.getCo2(),
                        resultData.getHappiness(),
                        ContentGenerator.getPlayerTypeByName(resultData.getPlayerTypeName()),
                        resultData.getItemsBought());
                finishedGames.add(result);
            }
            System.out.println("Your saved game was loaded.");
        } catch (SaveGameException e) {
            System.out.println("No saved game found - a new game has been created");
            System.out.println("[Debug] What actually went wrong loading the save: "+e.getMessage());
            System.out.println("[Debug] Cause: " + e.getCause());
            // finishedGames Arraylist will be an empty list
            // This is the same as starting a new game
        }
    }

    public void deleteSaveFile() {
        System.out.println("Game deleted !!!");
        saveGame.delete();
        loadGame();
    }

    /**
     * parsing arraylist of Rooms to an arraylist of IRoom and returns it
     */
    @Override
    public ArrayList<IRoom> getRooms() {
        return new ArrayList<>(rooms);
    }

    @Override
    public IPlayer getPlayer() {
        return player;
    }

    @Override
    public void setPlayerType(String playerType) {
        switch (playerType) {
            case "Student":
                this.player.setPlayerType(ContentGenerator.getPlayerTypeByName(ContentGenerator.STUDENT_NAME));
                break;
            case "Bodybuilder":
                this.player.setPlayerType(ContentGenerator.getPlayerTypeByName(ContentGenerator.BODYBUILDER_NAME));
                break;
            case "Picky":
                this.player.setPlayerType(ContentGenerator.getPlayerTypeByName(ContentGenerator.PICKY_NAME));
                break;
            case "Random":
                this.player.setPlayerType(ContentGenerator.getPlayerTypeByName(ContentGenerator.RANDOM_TYPE_NAME));
                break;
            case "Mystery":
                PlayerType newPicky = ContentGenerator.getPlayerTypeByName(ContentGenerator.PICKY_NAME);
                newPicky.setPlayerSprite(Game.class.getResource("/sprites/gurli.png").toString());
                this.player.setPlayerType(newPicky);
                newPicky.setValues(1200, 7500, 9000);
            default:
        }
    }

    @Override
    public PlayerType getPlayerType() {
        return player.getPlayerType();
    }


    /**
     * prints the properties of the playerType
     */
    public String getPlayerDescription() {
        StringBuilder returnString = new StringBuilder();
        returnString.append("You are playing as a " + player.getPlayerType().getName() + "\n");
        returnString.append(player.getPlayerType().getDescription());
        return returnString.toString();
    }

    public void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zhopping!");
        System.out.println("World of Zhopping is a new, incredibly fun shopping game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(rooms.get(0).getDescription());
    }

    private void save() {
        ArrayList<GameResultData> resultData = new ArrayList<>();
        for (GameResult result : finishedGames) {
            resultData.add(new GameResultData(result.getCo2(), result.getHappiness(), result.getPlayerType().getName(),result.getItemsBought()));
        }
        try {
            saveGame.save(resultData);
            System.out.println("Game saved successfully\n");
        } catch (SaveGameException e) {
            System.out.println("An error occurred when saving the game");
        }
    }

    public CheckoutReturnObject Checkout() {
        CheckoutReturnObject object = new CheckoutReturnObject();

        //Cannot checkout, return reason
        if (!getPlayer().underBudget()) {
            object.addReturnStrings("You are over budget. Drop some items (use \"drop\" command)");
            return object;
        }
        if (!getPlayer().overMinCalories()) {
            object.addReturnStrings("You are under your calorie requirements. Pick up some items (use \"take\" command)");
            return object;
        }

        //Can checkout, return result
        resetGame();

        int happiness = 0;
        double co2 = 0;
        int timesPlayed = 0; // to count how many times the game has played
        for (GameResult finishedGame : finishedGames) {
            happiness += finishedGame.getHappiness();
            co2 += finishedGame.getCo2();
            timesPlayed++;
        }

        if(co2 < 30 && happiness > -200){
            ArrayList<IItem> items = getPlayer().getInventory();
            object.addReturnStrings("You went to the cash register and checked out.\nThe day is over and you go back home to sleep.\n\n");
            if (isCo2Bad) {
                object.addReturnStrings(co2IsBadString(items));
            }
            if (isNotHappy) {
                object.addReturnStrings(playerTypeNotHappyString());
            }
            object.addReturnStrings("It is a new day, you wake up and go to the store.");

            object.setDidCheckout(true);
            object.addReturnStrings(reactToResults(co2,happiness,timesPlayed));
            return object;
        }

        //gameover
        object.setDidCheckout(true);
        object.setGameOver(true);

        object.addReturnStrings("Game Over");
        object.addReturnStrings("you reached day " + timesPlayed);

        //TODO add string with most poluting item purchased
        //TODO NOT DONE
        ArrayList<IItem> allItemsBought = new ArrayList<>();
        for (GameResult finishedGame : finishedGames) { //Go through the items backwards!
            if (!finishedGame.getPlayerType().getName().equalsIgnoreCase(this.player.getPlayerType().getName())) {
                //We only want the inventories of the times we played with the current player type.
                continue;
            }
            ArrayList<String> itemsBought = finishedGame.getItemsBought();
            for(String itemName : finishedGame.getItemsBought()) {
                for (IRoom room : rooms) {
                    allItemsBought.add(room.getItem(itemName));
                }
            }
        }
        //TODO NOT DONE


        if(co2 > 30){
            object.addReturnStrings("The store has burnt down\nno living beings are left\nyour consumption destroyed the world");
        }else {
            object.addReturnStrings("The worlds unhappiness has lead to revolution\nYou have successfully overthrown the government. \n Gasoline is now the only currency. \n\n\n");
        }

        deleteSaveFile();

        return object;
    }

    /**
     * Takes care of resetting player and rooms and prints stuff to the user
     */

    public void resetGame() {
        //Makes a uneven reversed 2D array of items the player bought all previous games.
        //May be empty.
        String[][] previousInventories = new String[finishedGames.size()][1];
        int q = finishedGames.size()-1; //Used to reverse the list.
        for (GameResult finishedGame : finishedGames) { //Go through the items backwards!
            if (!finishedGame.getPlayerType().getName().equalsIgnoreCase(this.player.getPlayerType().getName())) {
                //We only want the inventories of the times we played with the current player type.
                continue;
            }
            ArrayList<String> itemsBought = finishedGame.getItemsBought();
            previousInventories[q] = new String[itemsBought.size()];
            for (int j = 0; j < itemsBought.size(); j++) {
                previousInventories[q][j] = itemsBought.get(j);
            }
            q--;
        }
        GameResult result = player.getGameResult(previousInventories);
        finishedGames.add(result); //adds the game result of the currently played game to an arraylist of results.
        player.deleteInventory(); // deletes all items in the inventory
        player.setPlayerType(getPlayerType());
        save();
    }

    /**
     * Prints messages depending on co2 and happiness
     * the more co2 the player has emitted, the hotter the world will become
     * the more negative the players happiness, the more unhappy the player will be
     * This will be reflected in the printed messages
     */
    public String reactToResults(double co2, int happiness, int timesPlayed) {
        StringBuilder returnString = new StringBuilder();

        //Prints the results of the day if the player has played a game
        if (finishedGames.size()>0) {
            GameResult lastGame = finishedGames.get(finishedGames.size() - 1);
            returnString.append("Your results for today\n\n");
            returnString.append("CO2: ").append(String.format("%4.2f", lastGame.getCo2())).append("\n");
            returnString.append("Happiness: ").append(String.format("%2.0f", lastGame.getHappiness())).append("\n\n\n");
            if(finishedGames.size() == 1) {
                returnString.append("You have played: ").append(finishedGames.size()).append(" time. \n---\n");
            }
            else{
                returnString.append("You have played: ").append(finishedGames.size()).append(" times. \n---\n");
            }
        }

        returnString.append("Your results for today\n\n");
        returnString.append("CO2: ").append(co2).append("\n");
        returnString.append("Happiness: ").append(happiness).append("\n\n\n");
        returnString.append("You have played: ").append(timesPlayed).append(" times. \n");

        //co2
        returnString.append("Current climate situation: \n");

        if (co2 < 5) {
            isCo2Bad = false;
            returnString.append("The earth is still a green and beautiful place\n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/zuupermarket.png").toString());
        } else if (co2 < 10) {
            isCo2Bad = true;
            if (finishedGames.size()>1 && getLastGameCO2()>5 && getLastGameCO2()<10) {
                isCo2Bad = false;
            }
            returnString.append("You notice your armpits are more stained than usual.\n People seem to be rioting.");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/zuupermarket1.png").toString());

        } else if (co2 < 15) {
            isCo2Bad = true;
            if (finishedGames.size()>1 && getLastGameCO2()>10 && getLastGameCO2()<15) {
                isCo2Bad = false;
            }
            returnString.append("It's getting hot outside and\nyou notice that plants are dying around you. \n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/zuupermarket2.png").toString());

        } else if (co2 < 20) {
            isCo2Bad = true;
            if (finishedGames.size()>1 && getLastGameCO2()>15 && getLastGameCO2()<20) {
                isCo2Bad = false;
            }
            returnString.append("It's too hot to walk barefoot. \n You notice everything sets on fire.");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/zuupermarket3.png").toString());

        } else if (co2 < 25) {System.out.println(co2);
            isCo2Bad = true;
            if (finishedGames.size()>1 && getLastGameCO2()>20 && getLastGameCO2()<25) {
                isCo2Bad = false;
            }
            returnString.append("All the glaciers have melted and the ocean has risen. \n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/zuupermarket4.png").toString());

        } else {
            isCo2Bad = true;
            returnString.append("The world is burning down and\nthe store is set on fire.\n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/zuupermarket5.png").toString());
        }

        //happiness
        returnString.append("\nYour current situation: \n");

        if (happiness >= 0) {
            isNotHappy = false;
            returnString.append("You're feeling fine. \n");
        } else if (happiness > -50) {
            isNotHappy = true;
            if (finishedGames.size()>1 && getLastGameHappiness()<0 && getLastGameHappiness()>-25) {
                isNotHappy = false;
            }
            returnString.append("You notice that you've started snapping at your friends.\n");

        } else if (happiness > -100) {
            isNotHappy = true;
            if (finishedGames.size()>1 && getLastGameHappiness()<-50 && getLastGameHappiness()>-100) {
                isNotHappy = false;
            }
            returnString.append("You don't want to eat anymore. You hate yourself.\n");

         }else if (happiness > -150) {
            isNotHappy = true;
            if (finishedGames.size()>1 && getLastGameHappiness()<-100 && getLastGameHappiness()>-150) {
                isNotHappy = false;
            }
            returnString.append("You're beginning to wonder if there's a point to anything. \n");

        } else if (happiness > -200) {
            isNotHappy = true;
            if (finishedGames.size()>1 && getLastGameHappiness()<-150 && getLastGameHappiness()>-200) {
                isNotHappy = false;
            }
            returnString.append("You've joined a fascist movement.\n");
        }
        return returnString.toString();
    }


    /**
     * Adds an item from the room to the players inventory
     *
     * @param item second word should be the name of the item the player wants to pick up
     */
    @Override
    public boolean take(IItem item) {
        if (item instanceof Item) {
            player.addItem((Item) item);
            if (player.underBudget()) return true;

            player.removeItem((Item) item);
            return false;
        }
        return false;
    }

    /**
     * Removes an item from the players inventory
     *
     * @param item second word should be the name of the item the player wants to remove
     */
    @Override
    public void drop(IItem item) {
        if (item instanceof Item) {
            player.removeItem((Item) item);
        }
    }

    private String co2IsBadString(ArrayList<IItem> inventory) {
        String itemname = "";
        double co2 = 0;
        float co2Total = 0;


        for (IItem item : inventory) {
            co2Total += item.getCo2();

            if (item.getCo2() > co2) {
                co2 = item.getCo2();
                itemname = item.getName();

            }

        }

        String co2Percentage = String.format("%.2f", (co2 / co2Total) * 100);
        StringBuilder builder = new StringBuilder();
        builder.append("Your CO2 emissions today was very high:\n");
        builder.append("The item with the highest C02 emission was: " + itemname + "\nIt was responsible for " + co2Percentage + "% of the total emissions today.\n");
        builder.append("Consider buying less of that, and thinking more\nabout the environment.");
        return builder.toString();
    }

    private String playerTypeNotHappyString() {
        String reason = this.player.getPlayerType().getUnhappyReason();
        if(!reason.equals("")){
            return "The " + player.getPlayerType().getName() + " is not happy.\n" + reason + "\nTry buying different things tomorrow!";
        }else {
            return "The " + player.getPlayerType().getName() + " is not happy.\nTry buying their favorite item: " + player.getPlayerType().getRandomFaveItem() + "\nor try buying different things each day.";
        }
    }
    private double getLastGameCO2(){
        double total = 0;
        for(int i= 0; i<finishedGames.size()-1; i++){
            total += finishedGames.get(i).getCo2();
        }
        return total;
    }
    private double getLastGameHappiness(){
        double total = 0;
        for(int i= 0; i<finishedGames.size()-1; i++){
            total += finishedGames.get(i).getHappiness();
        }
        return total;
    }
}
