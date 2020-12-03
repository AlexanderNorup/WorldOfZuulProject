package worldofzuul.DomainLayer;

import worldofzuul.DataLayer.*;
import worldofzuul.DomainLayer.Commandhandling.Command;
import worldofzuul.DomainLayer.Commandhandling.CommandWord;
import worldofzuul.DomainLayer.Commandhandling.CommandWords;
import worldofzuul.DomainLayer.Commandhandling.Parser;
import worldofzuul.DomainLayer.Interfaces.IGame;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IPlayer;
import worldofzuul.DomainLayer.Interfaces.IRoom;

import java.util.ArrayList;


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
        try {
            ArrayList<GameResultData> loadedData = saveGame.load();
            for (GameResultData resultData : loadedData) {
                GameResult result = new GameResult(resultData.getCo2(),
                        resultData.getHappiness(),
                        ContentGenerator.getPlayerTypeByName(resultData.getPlayerTypeName()));
                finishedGames.add(result);
            }
            System.out.println("Your saved game was loaded");
        } catch (SaveGameException e) {
            System.out.println("No saved game found - created new game");
            // finishedGames Arraylist will be an empty list
            // This is the same as starting a new game
        }
    }

    /**
     * parsing arraylist of Rooms to an arraylist of IRoom and returns it
     */
    @Override
    public ArrayList<IRoom> getIRooms() {
        return new ArrayList<>(rooms);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    @Override
    public IPlayer getIPlayer(){
        return player;
    }

    public Player getPlayer(){
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
                newPicky.setPlayerSprite(Game.class.getResource("/sprites/Piggy.png").toString());
                this.player.setPlayerType(newPicky);
            default:
        }
    }


    /**
     * prints the properties of the playerType
     */
    public String printPlayer() {
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
        System.out.println(rooms.get(0).getLongDescription());
    }

    private void save() {
        ArrayList<GameResultData> resultData = new ArrayList<>();
        for (GameResult result : finishedGames) {
            resultData.add(new GameResultData(result.getCo2(), result.getHappiness(), result.getPlayerType().getName()));
        }
        try {
            saveGame.save(resultData);
            System.out.println("Game saved successfully");
        } catch (SaveGameException e) {
            System.out.println("An error occurred when saving the game");
        }
    }

    /**
     * Takes care of resetting player and rooms and prints stuff to the user
     */
    public void resetGame() {
        GameResult result = player.getGameResult();
        finishedGames.add(result); //adds the game result of the currently played game to an arraylist of results.
        player.deleteInventory(); // deletes all items in the inventory
        player.setPlayerType((ContentGenerator.getStudentPlayerType()));
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
        for (GameResult finishedGame : finishedGames) {
            happiness += finishedGame.getHappiness();
            co2 += finishedGame.getCo2();
        }

        returnString.append("CO2: ").append(co2);

        if (co2 < 5) {
            returnString.append("The earth is a green and beautiful place\n");
        } else if (co2 < 10) {
            returnString.append("you notice your armpits are more stained than usual\n");
            rooms.get(0).setBackground("supermarket1.png");
        } else if (co2 < 15) {
            returnString.append("it's to hot to walk barefoot\n");
            rooms.get(0).setBackground("supermarket2.png");
        } else if (co2 < 20) {
            returnString.append("you can boil an egg in the ocean\n");
            rooms.get(0).setBackground("supermarket3.png");
        } else if (co2 < 25) {
            returnString.append("you've sold your oven, as you don't need it\n");
            rooms.get(0).setBackground("supermarket4.png");
        } else {
            returnString.append("the store is on fire\n");
            rooms.get(0).setBackground("supermarket5.png");
        }

        if (happiness >= 0) {
            returnString.append("you're ok...\n");
        } else if (happiness > -50) {
            returnString.append("you notice that you've started snapping at your friends\n");
        } else if (happiness > -100) {
            returnString.append("you don't want to eat, even when you're hungry\n");
        } else if (happiness > -150) {
            returnString.append("you're wondering if theres a point to anything\n");
        } else if (happiness > -200) {
            returnString.append("you've joined a fascist movement\n");
        } else {
            returnString.append("you have successfully toppled the government, gasoline is the only currency\n");
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
