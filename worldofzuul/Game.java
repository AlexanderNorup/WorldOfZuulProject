package worldofzuul;

import java.util.ArrayList;

public class Game {
    private final Parser parser;
    private Room currentRoom;
    private final Player player;
    private final ArrayList<GameResult> finishedGames;

    public Game() {
        createRooms();
        parser = new Parser();
        player = new Player(ItemGenerator.randomPlayerType());
        finishedGames = new ArrayList<>();
    }

    //TODO: Create descriptive directions, add back command
    private void createRooms() {
        Room outside, aisle1, aisle2, aisle3, cashier, butcher, produce, frozen, dairy, bakery, tinnedGoods;

        outside = new Room("outside the main entrance of the store", false);
        aisle1 = new Room("in the 1st aisle. \nTo your east is the dairy, to your west is the bakery, " +
                "in the south is the 2nd aisle", false );
        aisle2 = new Room("in the 2nd aisle", false);
        aisle3 = new Room("in the 3rd aisle", false);
        dairy = new Room("in the dairy section", false, ItemGenerator.getDairyItems());
        bakery = new Room("at the bakery", false, ItemGenerator.getBakeryItems());
        frozen = new Room("in the frozen section", false, ItemGenerator.getFrozenItems());
        tinnedGoods = new Room("in the tinned goods section", false, ItemGenerator.getTinnedGoodsItems());
        produce = new Room("at the produce section",false, ItemGenerator.getProduceItems());
        butcher = new Room("at the butcher", false, ItemGenerator.getButcherItems());
        cashier = new Room("at the cashier.\n Use command 'checkout' to check out and finish the game ", true);

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

        cashier.setExit("north", aisle3);

        currentRoom = outside; //this sets the starting position to "outside"
    }

    public void play() {
        printWelcome();
        printPlayer();
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    public void printPlayer(){
        System.out.println("You are playing as a " + player.getPlayerType().getName());
        System.out.println("| Your budget is " + player.getPlayerType().getBudgetMax() + " dkk." + "  ||  " +
                "Your minimum calorie goal is " + player.getPlayerType().getCalorieMin() + "  ||  " +
                "Your minimum protein goal is " + player.getPlayerType().getProteinMin() + " |");
        //System.out.println("Your budget is " + player.getPlayerType().getBudgetMax() + " dkk.");
        //System.out.println("Your minimum calorie goal is " + player.getPlayerType().getCalorieMin());
        //System.out.println("Your minimum protein goal is " + player.getPlayerType().getProteinMin());
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
            case CHECKOUT -> checkout();

            default -> System.out.println("processCommand -> unregistered command!");
        }

        return wantToQuit;
    }

    private void checkout(){
        if (!currentRoom.canCheckout()){ //checks if it is possible to checkout in the current room.
            System.out.println("You can't checkout here, go to the cashier.");
            return;
        }
        GameResult result = player.getGameResult();
        finishedGames.add(result); //adds the game result of the currently played game to an arraylist of results.
        System.out.println("You went to the register and checked out.");
        System.out.println(player.getSummedValues()); //prints the values for the current shopping trip, price, calories & protein
        System.out.println("The day is over and you go back home to sleep.");
        resetGame(); //resets the game and starts anew.
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the store.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void resetGame(){
        player.deleteInventory(); // deletes all items in the inventory
        createRooms(); //creates the rooms again and fills them with items
        System.out.println(".\n" + ".\n" + ".\n" + ".\n" + ".\n" + "." );
        player.setPlayerType((ItemGenerator.randomPlayerType()));
        System.out.println("It is a new day, you wake up and go to the store.");
        printPlayer();
        System.out.println(currentRoom.getLongDescription());

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
        String word = command.getSecondWord();
        if (word == null || !word.equalsIgnoreCase("section") && !word.equalsIgnoreCase("inventory") ){
            System.out.println("Check what? (Section or Inventory)");
            return;
        }

        if(word.equalsIgnoreCase("section")){ //checks if the second word is section
            String string = currentRoom.getItemsString();
            System.out.println(string != null ? string : "no products in section"); //prints items from the current room
        }
        else if(word.equalsIgnoreCase("inventory")){ //checks if the second word is inventory
            String string = player.getInventoryString();
            System.out.println(string != null ? string : "no products in inventory"); //prints items from player inventory
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
            player.addItem(item);
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
        }else {
            System.out.println("'"+command.getSecondWord()+"' not found in inventory");
        }
        //if item null, print "'itemname' not found in inventory"
    }
}
