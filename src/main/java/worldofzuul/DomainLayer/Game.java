package worldofzuul.DomainLayer;

import worldofzuul.DataLayer.*;
import worldofzuul.CLILayer.CommandWord;
import worldofzuul.DomainLayer.Interfaces.IGame;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IPlayer;
import worldofzuul.DomainLayer.Interfaces.IRoom;

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

    public Game() {
        rooms = ContentGenerator.getRooms();
        player = new Player(ContentGenerator.getRandomPlayerType(),
                            this.rooms.get(0)); //Set's starting room to the first room (outside).

        finishedGames = new ArrayList<>();
        saveGame = new SaveFile("./saveFile.json");
        loadGame();
        reactToResults();
    }

    void loadGame() {
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
    public IPlayer getPlayer(){
        return player;
    }

    @Override
    public void setPlayerType(String playerType) {
        switch (playerType){
            case "Student":
                this.player.setPlayerType(ContentGenerator.getStudentPlayerType());
                break;
            case "Bodybuilder":
                this.player.setPlayerType(ContentGenerator.getBodybuilderPlayerType());
                break;
            case "Picky":
                this.player.setPlayerType(ContentGenerator.getPickyPlayerType());
                break;
            case "Random":
                this.player.setPlayerType(ContentGenerator.getRandomPlayerType());
                break;
            case "Mystery":
                PlayerType newPicky = ContentGenerator.getPickyPlayerType();
                newPicky.setPlayerSprite(Game.class.getResource("/sprites/gurli.png").toString());
                this.player.setPlayerType(newPicky);
                newPicky.setValues(1200,7500,9000);
            default:
        }
    }

    @Override
    public PlayerType getPlayerType(){
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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
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

    public String canCheckout(){
        if (!getPlayer().underBudget()) {
            return "You are over budget. Drop some items (use \"drop\" command)";
        }
        if (!getPlayer().overMinCalories()) {
            return "You are under your calorie requirements. Pick up some items (use \"take\" command)";
        }
        return null;
    }

    public ArrayList<String> Checkout(){
        ArrayList<String> strings = new ArrayList<>();

        strings.add("You went to the cash register and checked out.\nThe day is over and you go back home to sleep.\n\n");
        //strings.add(player.getGameResult());
        resetGame();
        strings.add(reactToResults());
        strings.add("It is a new day, you wake up and go to the store.");

        return strings;
    }

    /**
     * Takes care of resetting player and rooms and prints stuff to the user
     */
    public void resetGame() {
        //Makes a uneven reversed 2D array of items the player bought all previous games.
        //May be empty.
        String[][] previousInventories = new String[finishedGames.size()][1];
        int q = finishedGames.size()-1; //Used to reverse the list.
        for(int i = 0; i < finishedGames.size(); i++){ //Go through the items backwards!
            ArrayList<String> itemsBought = finishedGames.get(i).getItemsBought();
            previousInventories[q] = new String[itemsBought.size()];
            for(int j = 0; j < itemsBought.size(); j++){
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
    @Override
    public String reactToResults() {
        StringBuilder returnString = new StringBuilder();
        int happiness = 0;
        double co2 = 0;
        int timesPlayed=0; // to count how many times the game has played
        for (GameResult finishedGame : finishedGames) {
            happiness += finishedGame.getHappiness();
            co2 += finishedGame.getCo2();
            timesPlayed++;
        }
        returnString.append("Your results for today\n\n");
        returnString.append("CO2: ").append(co2).append("\n");
        returnString.append("Happiness: ").append(happiness).append("\n\n\n");
        returnString.append("You have played: ").append(timesPlayed).append(" times. \n---\n");

        returnString.append("Current climate situation: \n");
        if (co2 < 5) {
            returnString.append("The earth is still a green and beautiful place\n");
        } else if (co2 < 10) {
            returnString.append("You notice your armpits are more stained than usual.\n People seem to be rioting.");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/supermarket1.png").toString());
        } else if (co2 < 15) {
            returnString.append("It's getting hot outside and you notice that plants are dying around you. \n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/supermarket2.png").toString());
        } else if (co2 < 20) {
            returnString.append("It's too hot to walk barefoot. \n You notice everything sets on fire.");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/supermarket3.png").toString());
        } else if (co2 < 25) {
            returnString.append("All the glaciers have melted and the ocean has risen. \n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/supermarket4.png").toString());
        } else {
            returnString.append("The world is burning down and the store is set on fire.\n");
            rooms.get(0).setBackground(Game.class.getResource("/backgrounds/supermarket5.png").toString());
        }
        returnString.append("\nYour current situation: \n");
        if (happiness >= 0) {
            returnString.append("You're feeling fine. \n");
        } else if (happiness > -50) {
            returnString.append("You notice that you've started snapping at your friends.\n");
        } else if (happiness > -100) {
            returnString.append("You don't want to eat anymore. You hate yourself.\n");
        } else if (happiness > -150) {
            returnString.append("You're beginning to wonder if there's a point to anything. \n");
        } else if (happiness > -200) {
            returnString.append("You've joined a fascist movement.\n");
        } else {
            returnString.append("You have successfully overthrown the government. \n Bottle caps are now the only currency. \n");
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
        if(item instanceof Item){
            player.addItem((Item) item);
            if(player.underBudget()) return true;

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
        if(item instanceof Item){
            player.removeItem((Item) item);
        }
    }
}
