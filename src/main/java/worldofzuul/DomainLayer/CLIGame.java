package worldofzuul.DomainLayer;

import worldofzuul.DataLayer.ContentGenerator;
import worldofzuul.DomainLayer.Commandhandling.Command;
import worldofzuul.DomainLayer.Commandhandling.CommandWord;
import worldofzuul.DomainLayer.Commandhandling.Parser;

public class CLIGame {

    private Room currentRoom;

    private final Parser parser;
    Game game;

    public CLIGame(){
        game =  new Game();
        parser = new Parser();
        currentRoom = game.getRooms().get(0);
    }

    /**
     * Print welcome message and runs an "infinite" while loop
     * continuously getting new commands and processing them.
     */
    public void play() {
        game.printWelcome();
        System.out.println(game.printPlayer());
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
            returnString.append("You are out shopping as a " + game.getIPlayer().getPlayerType().getName() + "\n");
            returnString.append("| Your budget is " + game.getIPlayer().getPlayerType().getBudgetMax() + " dkk." + "  ||  " +
                    "Your minimum calorie goal is " + game.getIPlayer().getPlayerType().getCalorieMin() + "  |\n");
            returnString.append("\n");
            returnString.append("Your command words are:\n");
            returnString.append(parser.showCommands());
            returnString.append("Tip: You can type 'help' and then the command you want to know more about. " +
                    "('help command')\n");
            return returnString.toString();
        }
    }

    /**
     * Extracts the CommandWord from the Command
     * calls the appropriate  method for processing the command
     *
     * @param command Command to be processed
     * @return false if the CommandWord is quit. True otherwise
     */
    public String processCommand(Command command) {

        CommandWord commandWord = command.getCommandWord();
        String output;
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
     * Adds an item from the room to the players inventory
     *
     * @param command second word should be the name of the item the player wants to pick up
     */
    private String take(Command command) {
        System.out.println("COMMAND - take");
        System.out.println("item name: " + command.getSecondWord());
        Item item = currentRoom.getItem(command.getSecondWord());
        System.out.println("item null: " + (item == null));
        if (item != null) {
            game.take(item);
            return "You picked up " + item.getName();
        } else {
            return "'" + command.getSecondWord() + "' not found in store";
        }
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
            String string = game.getPlayer().getInventoryString();
            return string != null ? string : "No items in inventory"; //gets  items from player inventory
        }
        return "Check what?";
    }

    /**
     * prints information about the specified item
     *
     * @param command second word should be the name of the item the player wants to inspect
     */
    private String inspect(Command command) {
        Item item = currentRoom.getItem(command.getSecondWord());
        return item != null ? item.toString() : "item not found";
    }


    /**
     * Checks out IF the player is at the cash register and the player has reached their
     * calorie goal without using up their budget
     * if everything is alright adds a new gameResult to the list of results and restarts the game
     */
    private String checkout() {
        System.out.println("checkout");

        if (!currentRoom.canCheckout()) { //checks if it is possible to checkout in the current room.
            return "You can't checkout here, go to the cashier.";
        }
        if (!game.getPlayer().underBudget()) {
            return "You are over budget. Drop some items (use \"drop\" command)";
        }
        if (!game.getPlayer().overMinCalories()) {
            return "You are under your calorie requirements. Pick up some items (use \"take\" command)";
        }

        //TODO implement correct checkout procedure

        StringBuilder returnString = new StringBuilder();
        returnString.append("You went to the register and checked out.");
        returnString.append("The day is over and you go back home to sleep.");
        returnString.append(resetGame()); //resets the game and starts anew.

        returnString.append(game.reactToResults());
        setStartPosition(); //creates the rooms again and fills them with items
        returnString.append(".\n" + ".\n" + ".\n" + ".\n" + ".\n" + ".");
        returnString.append("It is a new day, you wake up and go to the store.");
        returnString.append(game.printPlayer());
        returnString.append(currentRoom.getLongDescription());
        return returnString.toString();
    }


    /**
     * Creates all the rooms in the shopping mall and connects them with setExit
     * Also populates some of the rooms with items
     * Sets the current room
     */
    private void setStartPosition() {
        currentRoom = game.getRooms().get(0); //this sets the starting position to the first room. (Which will always be outside).
    }

    /**
     * Takes care of resetting player and rooms and prints stuff to the user
     */
    private String resetGame() {
        StringBuilder returnString = new StringBuilder();
        returnString.append(game.reactToResults());
        game.getPlayer().deleteInventory(); // deletes all items in the inventory
        setStartPosition(); //creates the rooms again and fills them with items
        returnString.append(".\n" + ".\n" + ".\n" + ".\n" + ".\n" + ".");
        game.getPlayer().setPlayerType((ContentGenerator.getStudentPlayerType()));
        returnString.append("It is a new day, you wake up and go to the store.");
        returnString.append(game.printPlayer());
        returnString.append(currentRoom.getLongDescription());
        return returnString.toString();
    }

    /**
     * Removes an item from the players inventory
     *
     * @param command second word should be the name of the item the player wants to remove
     */
    private String drop(Command command) {

        //get item from user
        Item item = game.getPlayer().getItem(command.getSecondWord());

        //if item not null, remove item from inventory and add to store
        if (item != null) {
            game.drop(item);
            return "You dropped " + item.getName();
        } else {
            return "'" + command.getSecondWord() + "' not found in inventory";
        }
        //if item null, print "'itemname' not found in inventory"
    }


    /**
     * Ends the game if the command has no second word
     *
     * @param command Used to make sure there isn't a second word.
     * @return whether the game is to be ended
     */
    private boolean quit(Command command) {
        return !command.hasSecondWord();
    }
}
