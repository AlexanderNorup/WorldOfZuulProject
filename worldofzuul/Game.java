package worldofzuul;

import java.util.ArrayList;

public class Game {
    private final Parser parser;
    private Room currentRoom;
    private Player player;

    public Game() {
        createRooms();
        parser = new Parser();

    }

    //TODO: Create descriptive directions, add back command
    private void createRooms() {
        Room outside, aisle1, aisle2, aisle3, cashier, butcher, produce, frozen, dairy, bakery, tinnedGoods;

        outside = new Room("outside the main entrance of the store", new ArrayList<>());
        aisle1 = new Room("in 1st ile");
        aisle2 = new Room("in 2nd ile");
        aisle3 = new Room("in 3rd ile");
        butcher = new Room("at the butcher", ItemGenerator.getButcherItems());
        produce = new Room("at the produce section", ItemGenerator.getProduceItems());
        frozen = new Room("in the frozen section", ItemGenerator.getFrozenItems());
        dairy = new Room("in the dairy section", ItemGenerator.getDairyItems());
        bakery = new Room("ad the bakery", ItemGenerator.getBakeryItems());
        tinnedGoods = new Room("in the tinned goods ile", ItemGenerator.getTinnedGoodsItems());
        cashier = new Room("at the cashier");

        outside.setExit("north", aisle1);

        aisle1.setExit("north", aisle2);
        aisle1.setExit("east", tinnedGoods);
        aisle1.setExit("west", frozen);

        aisle2.setExit("north", aisle3);
        aisle2.setExit("east", dairy);
        aisle2.setExit("west", bakery);

        aisle3.setExit("north", cashier);
        aisle3.setExit("east", butcher);
        aisle3.setExit("west", produce);

        outside.setExit("south", aisle1);

        aisle1.setExit("east", dairy);
        dairy.setExit("west", aisle1);
        aisle1.setExit("west", bakery);
        bakery.setExit("east", aisle1);
        aisle1.setExit("south", aisle2);

        aisle2.setExit("north", aisle1);
        aisle2.setExit("east", frozen);
        frozen.setExit("west", aisle2);
        aisle2.setExit("west", tinnedGoods);
        tinnedGoods.setExit("east", aisle2);
        aisle2.setExit("south", aisle3);

        aisle3.setExit("north", aisle2);
        aisle3.setExit("east", produce);
        produce.setExit("west", aisle3);
        aisle3.setExit("west", butcher);
        butcher.setExit("east", aisle3);
        aisle3.setExit("south", cashier);

        currentRoom = outside; //this sets the starting position to "outside"
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    //TODO: add Check inventory method
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case GO -> goRoom(command);
            case HELP -> printHelp();
            case QUIT -> wantToQuit = quit(command);
            case UNKNOWN -> System.out.println("I don't know what you mean");
            case INSPECT -> inspect(command);
            case DROP -> drop(command);
            case TAKE -> take(command);
            case CHECK -> check(command);

            default -> System.out.println("processCommand -> unregistered command!");
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the store.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    //checks if there is a second word when calling the check command and if it is either section or inventory.
    private void check(Command command) {
        if (!command.hasSecondWord() || !command.getSecondWord().equalsIgnoreCase("section") ||
!command.getSecondWord().equalsIgnoreCase("inventory") ){
            System.out.println("Check what? (Section or Inventory)");
            return;
        }
        String checker = command.getSecondWord();

        if(checker.equalsIgnoreCase("section")){ //checks if the second word is section
            System.out.println(currentRoom.getItemsString()); //prints items from the current room
        }
        else if(checker.equalsIgnoreCase("inventory")){ //checks if the second word is inventory
            System.out.println(player.getInventoryString()); //prints items from player inventory
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    //TODO:Check both Player and Room for item
    private void inspect(Command command){
        Item item = currentRoom.getItem(command.getSecondWord());
        System.out.println(item != null ? item.toString() : "item not found");
    }

    //TODO: add item to inventory
    private void take(Command command){
        Item item = currentRoom.getItem(command.getSecondWord());
        if(item != null){
            currentRoom.removeItem(item);
            //add item to inventory
        }else {
            System.out.println("'" + command.getSecondWord() + "' not found in store");
        }
    }

    //TODO: move item from inventory to room
    private void drop(Command command){

        //get item from user
        Item item = player.getItem(command.getSecondWord());

        //if item not null, remove item from inventory and add to store
        if(item != null){
            player.removeItem(item);
            currentRoom.addItem(item);
        }else {
            System.out.println("'itemname' not found in inventory");
        }
    }
}
