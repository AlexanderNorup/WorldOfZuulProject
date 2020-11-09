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
        //player = new Player(ItemGenerator.randomPlayerType());
        player = new Player(ItemGenerator.getStudentPlayerType());
        finishedGames = new ArrayList<>();
    }

    //TODO: add back command?
    private void createRooms() {
        Room outside, aisle1, aisle2, aisle3, cashier, butcher, produce, frozen, dairy, bakery, tinnedGoods;

        outside = new Room("outside the main entrance of the store\nThe entrance is to your south", false);
        aisle1 = new Room("in the 1st aisle. \nTo your east is the dairy section, to your west is the bakery, " +
                "to your south is the 2nd aisle", false );
        aisle2 = new Room("in the 2nd aisle. \nTo your east is the frozen section, to your west is the " +
                "Tinned goods section, to your north is the 1st aisle, to your south is the 2nd aisle", false);
        aisle3 = new Room("in the 3rd aisle. \nTo your east is the produce section, to your west is the " +
                "butcher, to your north is the 2nd aisle, to your south is the cashier", false);
        dairy = new Room("in the dairy section\nTo your west is the 1st aisle", false, ItemGenerator.getDairyItems());
        bakery = new Room("at the bakery\nTo your east is the 1st aisle", false, ItemGenerator.getBakeryItems());
        frozen = new Room("in the frozen section. \nTo your west is aisle 2", false, ItemGenerator.getFrozenItems());
        tinnedGoods = new Room("in the tinned goods section. \nTo your east is aisle 2", false, ItemGenerator.getTinnedGoodsItems());
        produce = new Room("at the produce section. \nTo your west is the 3. aisle",false, ItemGenerator.getProduceItems());
        butcher = new Room("at the butcher. \nTo your east is the 3. aisle", false, ItemGenerator.getButcherItems());
        cashier = new Room("at the cashier.\nUse command 'checkout' to checkout and finish the game ", true);

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
        System.out.println(player.getPlayerType().getDescription());
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

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
        if(!player.underBudget()){
            System.out.println("You are over budget. Drop some items (use \"drop\" command)");
            return;
        }
        if(!player.overMinCalories()){
            System.out.println("You are under your calorie requirements. Pick up some items (use \"take\" command)");
            return;
        }

        GameResult result = player.getGameResult();
        finishedGames.add(result); //adds the game result of the currently played game to an arraylist of results.
        System.out.println("You went to the register and checked out.");
        System.out.println(player.getSummedValuesString()); //prints the values for the current shopping trip, price, calories & protein
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
        reactToResults();
        player.deleteInventory(); // deletes all items in the inventory
        createRooms(); //creates the rooms again and fills them with items
        System.out.println(".\n" + ".\n" + ".\n" + ".\n" + ".\n" + "." );
        player.setPlayerType((ItemGenerator.randomPlayerType()));
        System.out.println("It is a new day, you wake up and go to the store.");
        printPlayer();
        System.out.println(currentRoom.getLongDescription());
    }

    private void reactToResults(){
        int happiness = 0;
        double co2 = 0;
        for(GameResult finishedGame : finishedGames){
            happiness += finishedGame.getHappiness();
            co2 += finishedGame.getCo2();
        }

        if (co2 < 100) {
            System.out.println("you notice your armpits are more stained than usual");
        }else if(co2 < 200){
            System.out.println(" it's to hot to walk barefoot");
        }else if(co2 < 300){
            System.out.println("you can boil an egg in the ocean");
        }else if(co2 < 400){
            System.out.println("you've sold your oven, as you don't need it");
        }else {
            System.out.println("the store is on fire");
        }

        if (happiness < 100) {
            System.out.println("you notice that you've started snapping at your friends");
        }else if(happiness < 200){
            System.out.println("you don't want to eat, even when your hungry");
        }else if(happiness < 300){
            System.out.println("you're wondering if theres a point to anything");
        }else if(happiness < 400){
            System.out.println("you've joined a fascist movement");
        }else {
            System.out.println("you have successfully toppled the government, gasoline is the only currency");
        }
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
    //TODO print total value
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

    private void take(Command command){
        Item item = currentRoom.getItem(command.getSecondWord());
        if(item != null){
            player.addItem(item);
            System.out.println("You picked up " + item.getName());
        }else {
            System.out.println("'" + command.getSecondWord() + "' not found in store");
        }
    }

    private void drop(Command command){

        //get item from user
        Item item = player.getItem(command.getSecondWord());

        //if item not null, remove item from inventory and add to store
        if(item != null){
            player.removeItem(item);
            System.out.println("You dropped " + item.getName());
        }else {
            System.out.println("'"+command.getSecondWord()+"' not found in inventory");
        }
        //if item null, print "'itemname' not found in inventory"
    }
}
