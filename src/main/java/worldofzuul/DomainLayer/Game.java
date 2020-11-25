package worldofzuul.DomainLayer;

import worldofzuul.DataLayer.GameResultData;
import worldofzuul.DataLayer.ISaveGame;
import worldofzuul.DataLayer.SaveFile;
import worldofzuul.DataLayer.SaveGameException;
import worldofzuul.DomainLayer.Commandhandling.Command;
import worldofzuul.DomainLayer.Commandhandling.CommandWord;
import worldofzuul.DomainLayer.Commandhandling.CommandWords;
import worldofzuul.DomainLayer.Commandhandling.Parser;
import worldofzuul.DomainLayer.Interfaces.IGame;
import worldofzuul.DomainLayer.Interfaces.IPlayer;
import worldofzuul.DomainLayer.Interfaces.IRoom;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The main class of the game
 * Processes the commands from the parser and takes care of all writing to the screen
 * This keeps track of the current room, the player and a list of finished games intended
 * for calculating a total score across multiple games.
 * There is a method for each command the user can enter
 * They are all called from the processCommand function
 */

public class Game implements IGame {
    private final Parser parser;
    private Room currentRoom;
    private final Player player;
    private final ArrayList<GameResult> finishedGames;
    private ISaveGame saveGame;

    public Game() {
        createRooms();
        parser = new Parser();
        player = new Player(ContentGenerator.getRandomPlayerType());
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

    @Override
    public ArrayList<IRoom> getRooms() {
        throw new UnsupportedOperationException("not implemented");
        //TODO implement
    }

    @Override
    public IPlayer getPlayer(){
        return player;
    }

    public String doAction(String firstWord, String secondWord) {
        CommandWords commands = new CommandWords();
        return processCommand(new Command(commands.getCommandWord(firstWord), secondWord));
    }



    /**
     * Creates all the rooms in the shopping mall and connects them with setExit
     * Also populates some of the rooms with items
     * Sets the current room
     */
    //TODO: add back command?
    private void createRooms() {
        ArrayList<Room> rooms = ContentGenerator.getRooms();
        currentRoom = rooms.get(0); //this sets the starting position to the first room. (Which will always be outside).
    }

    /**
     * Print welcome message and runs an "infinite" while loop
     * continuously getting new commands and processing them.
     */
    public void play() {
        printWelcome();
        System.out.println(printPlayer());
        while (true) {
            Command command = parser.getCommand();
            String output = processCommand(command);
            if (output.equals("$QUIT_CONFIRMED$")) {
                break;
            }
            System.out.println(output);
        }
        System.out.println("Thank you for playing.  Good bye.");
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

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Extracts the CommandWord from the Command
     * calls the appropriate  method for processing the command
     *
     * @param command Command to be processed
     * @return false if the CommandWord is quit. True otherwise
     */
    private String processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        String output = "";
        switch (commandWord) {
            case GO -> output = goRoom(command);
            case HELP -> output = getHelp(command);
            case QUIT -> output = quit(command) ? "$QUIT_CONFIRMED$" : "Quit what?";
            case UNKNOWN -> output = "I don't know what you mean";
            case INSPECT -> output = inspect(command);
            case DROP -> output = drop(command);
            case TAKE -> output = take(command);
            case CHECK -> output = check(command);
            case CHECKOUT -> output = checkout();

            default -> output = "processCommand -> unregistered command!";
        }

        return output;
    }

    /**
     * Checks out IF the player is at the cash register and the player has reached their
     * calorie goal without using up their budget
     * if everything is alright adds a new gameResult to the list of results and restarts the game
     */
    private String checkout() {
        if (!currentRoom.canCheckout()) { //checks if it is possible to checkout in the current room.
            return "You can't checkout here, go to the cashier.";
        }
        if (!player.underBudget()) {
            return "You are over budget. Drop some items (use \"drop\" command)";
        }
        if (!player.overMinCalories()) {
            return "You are under your calorie requirements. Pick up some items (use \"take\" command)";
        }

        GameResult result = player.getGameResult();
        finishedGames.add(result); //adds the game result of the currently played game to an arraylist of results.

        save();
        StringBuilder returnString = new StringBuilder();
        returnString.append("You went to the register and checked out.");
        returnString.append(player.getSummedValuesString()); //prints the values for the current shopping trip, price, calories & protein
        returnString.append("The day is over and you go back home to sleep.");
        returnString.append(resetGame()); //resets the game and starts anew.
        return returnString.toString();
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

    private String getHelp(Command command) {
        if (command.hasSecondWord()) { //if there's a second word when you type 'help' it enters the switch statement
            String com = command.getSecondWord(); //sets the second word of the command to be a string.
            switch (com) { //switch statement to print out advice on how to use commands.
                case "drop":
                    return "Type 'drop' and then the full name of the item you want to drop from " +
                            "your inventory, i.e 'drop 500g Ground Meat'.";
                case "help":
                    return "Prints out your current character and available commands.";
                case "take":
                    return "Type 'take' and then the full name of the item you want to take, " +
                            "i.e. 'take 500g Ground Meat'.";
                case "go":
                    return "Type 'go' and then the direction you want to head towards, i.e. 'go east'.";
                case "inspect":
                    return "Type 'inspect' and then the full name of the item you want to inspect and " +
                            "know more about, i.e. 'inspect 500g Ground Meat'.";
                case "quit":
                    return "Quits and ends the game.";
                case "check":
                    return "Type 'check' and then 'inventory' or 'section' to get a list of items " +
                            "in either your inventory or the current room, i.e. 'check section'.";
                case "checkout":
                    return "Type 'checkout' to finish your current shopping trip, only possible " +
                            "to do in the cashier room.";
                case "me":
                    return "Sorry, there's no help for you, you are stuck in here forever.";
                default:
                    return "Help with what command?";
            }
        } else {
            StringBuilder returnString = new StringBuilder();
            returnString.append("You are out shopping as a " + player.getPlayerType().getName() + "\n");
            returnString.append("| Your budget is " + player.getPlayerType().getBudgetMax() + " dkk." + "  ||  " +
                    "Your minimum calorie goal is " + player.getPlayerType().getCalorieMin() + "  |\n");
            returnString.append("\n");
            returnString.append("Your command words are:\n");
            returnString.append(parser.showCommands());
            returnString.append("Tip: You can type 'help' and then the command you want to know more about. " +
                    "('help command')\n");
            return returnString.toString();
        }
    }

    /**
     * Takes care of resetting player and rooms and prints stuff to the user
     */
    private String resetGame() {
        StringBuilder returnString = new StringBuilder();
        returnString.append(reactToResults());
        player.deleteInventory(); // deletes all items in the inventory
        createRooms(); //creates the rooms again and fills them with items
        returnString.append(".\n" + ".\n" + ".\n" + ".\n" + ".\n" + ".");
        player.setPlayerType((ContentGenerator.getStudentPlayerType()));
        returnString.append("It is a new day, you wake up and go to the store.");
        returnString.append(printPlayer());
        returnString.append(currentRoom.getLongDescription());
        return returnString.toString();
    }

    /**
     * Prints messages depending on co2 and happiness
     * the more co2 the player has emitted, the hotter the world will become
     * the more negative the players happiness, the more unhappy the player will be
     * This will be reflected in the printed messages
     */
    private String reactToResults() {
        StringBuilder returnString = new StringBuilder();
        int happiness = 0;
        double co2 = 0;
        for (GameResult finishedGame : finishedGames) {
            happiness += finishedGame.getHappiness();
            co2 += finishedGame.getCo2();
        }

        returnString.append("CO2: " + co2);

        if (co2 < 5) {
            returnString.append("The earth is a green and beautiful place\n");
        } else if (co2 < 10) {
            returnString.append("you notice your armpits are more stained than usual\n");
        } else if (co2 < 15) {
            returnString.append("it's to hot to walk barefoot\n");
        } else if (co2 < 20) {
            returnString.append("you can boil an egg in the ocean\n");
        } else if (co2 < 25) {
            returnString.append("you've sold your oven, as you don't need it\n");
        } else {
            returnString.append("the store is on fire\n");
        }

        if (happiness >= 0) {
            returnString.append("you're ok...\n");
        } else if (happiness > -50) {
            returnString.append("you notice that you've started snapping at your friends\n");
        } else if (happiness > -100) {
            returnString.append("you don't want to eat, even when your hungry\n");
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
     * @param command Command with a second word which is the name of the room to go to
     */
    private String goRoom(Command command) {
        if (!command.hasSecondWord()) {
            return "Go where?";
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            return "There is no door!";
        } else {
            currentRoom = nextRoom;
            return currentRoom.getLongDescription();
        }
    }

    /**
     * Prints the items in the inventory or current room (if any)
     * Prints "no products in section/inventory" otherwise
     *
     * @param command Command. Second word should be "inventory" or "section"
     */
    //checks if there is a second word when calling the check command and if it is either section or inventory.
    private String check(Command command) {
        String word = command.getSecondWord();
        if (word == null || !word.equalsIgnoreCase("section") && !word.equalsIgnoreCase("inventory")) {
            return "Check what? (Section or Inventory)";
        }

        if (word.equalsIgnoreCase("section")) { //checks if the second word is section
            String string = currentRoom.getItemsString();
            if (currentRoom.getItemsString() == null && !currentRoom.canCheckout()) { //checks if the room is an aisle
                return "This is an aisle you idiot! There are no products here!";
            } else {
                return string != null ? string : "No products in section"; //returns items from the current room
            }
        } else if (word.equalsIgnoreCase("inventory")) { //checks if the second word is inventory
            String string = player.getInventoryString();
            return string != null ? string : "No items in inventory"; //gets  items from player inventory
        }
        return "Check what?";
    }

    /**
     * Ends the game if the command has no second word
     *
     * @param command Used to make sure there isn't a second word.
     * @return whether the game is to be ended
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * prints information about the specified item
     *
     * @param command second word should be the name of the item the player wants to inspect
     */
    //TODO:Check both Player and Room for item
    private String inspect(Command command) {
        Item item = currentRoom.getItem(command.getSecondWord());
        return item != null ? item.toString() : "item not found";
    }

    /**
     * Adds an item from the room to the players inventory
     *
     * @param command second word should be the name of the item the player wants to pick up
     */
    private String take(Command command) {
        Item item = currentRoom.getItem(command.getSecondWord());
        if (item != null) {
            player.addItem(item);
            return "You picked up " + item.getName();
        } else {
            return "'" + command.getSecondWord() + "' not found in store";
        }
    }

    /**
     * Removes an item from the players inventory
     *
     * @param command second word should be the name of the item the player wants to remove
     */
    private String drop(Command command) {

        //get item from user
        Item item = player.getItem(command.getSecondWord());

        //if item not null, remove item from inventory and add to store
        if (item != null) {
            player.removeItem(item);
            return "You dropped " + item.getName();
        } else {
            return "'" + command.getSecondWord() + "' not found in inventory";
        }
        //if item null, print "'itemname' not found in inventory"
    }


}
