package worldofzuul.DomainLayer;

import worldofzuul.DataLayer.GameResultData;
import worldofzuul.DataLayer.ISaveGame;
import worldofzuul.DataLayer.SaveFile;
import worldofzuul.DataLayer.SaveGameException;
import worldofzuul.DomainLayer.Commandhandling.Command;
import worldofzuul.DomainLayer.Commandhandling.CommandWord;
import worldofzuul.DomainLayer.Commandhandling.Parser;

import java.util.ArrayList;


/**
 * The main class of the game
 * Processes the commands from the parser and takes care of all writing to the screen
 * This keeps track of the current room, the player and a list of finished games intended
 * for calculating a total score across multiple games.
 * There is a method for each command the user can enter
 * They are all called from the processCommand function
 */
public class Game {
    private final Parser parser;
    private Room currentRoom;
    private final Player player;
    private final ArrayList<GameResult> finishedGames;
    private ISaveGame saveGame;

    public Game() {
        createRooms();
        parser = new Parser();
        player = new Player(ContentGenerator.getStudentPlayerType());
        finishedGames = new ArrayList<>();
        saveGame = new SaveFile("./saveFile.json");
        try {
            ArrayList<GameResultData> loadedData = saveGame.load();
            for (GameResultData resultData: loadedData) {
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
     * Creates all the rooms in the shopping mall and connects them with setExit
     * Also populates some of the rooms with items
     * Sets the current room
     */
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
        dairy = new Room("in the dairy section\nTo your west is the 1st aisle", false, ContentGenerator.getDairyItems());
        bakery = new Room("at the bakery\nTo your east is the 1st aisle", false, ContentGenerator.getBakeryItems());
        frozen = new Room("in the frozen section. \nTo your west is aisle 2", false, ContentGenerator.getFrozenItems());
        tinnedGoods = new Room("in the tinned goods section. \nTo your east is aisle 2", false, ContentGenerator.getTinnedGoodsItems());
        produce = new Room("at the produce section. \nTo your west is the 3. aisle",false, ContentGenerator.getProduceItems());
        butcher = new Room("at the butcher. \nTo your east is the 3. aisle", false, ContentGenerator.getButcherItems());
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

    /**
     * Print welcome message and runs an "infinite" while loop
     * continuously getting new commands and processing them.
     */
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

    /**
     * prints the properties of the playerType
     */
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

    /**
     * Extracts the CommandWord from the Command
     * calls the appropriate  method for processing the command
     * @param command Command to be processed
     * @return false if the CommandWord is quit. True otherwise
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case GO -> goRoom(command);
            case HELP -> printHelp(command);
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

    /**
     * Checks out IF the player is at the cash register and the player has reached their
     * calorie goal without using up their budget
     * if everything is alright adds a new gameResult to the list of results and restarts the game
     */
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

        save();

        System.out.println("You went to the register and checked out.");
        System.out.println(player.getSummedValuesString()); //prints the values for the current shopping trip, price, calories & protein
        System.out.println("The day is over and you go back home to sleep.");
        resetGame(); //resets the game and starts anew.
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

    private void printHelp(Command command) {
        if (command.hasSecondWord()) { //if there's a second word when you type 'help' it enters the switch statement
            String com = command.getSecondWord(); //sets the second word of the command to be a string.
            switch (com){ //switch statement to print out advice on how to use commands.
                case "drop":
                    System.out.println("Type 'drop' and then the full name of the item you want to drop from " +
                            "your inventory, i.e 'drop 500g Ground Meat'.");
                    break;
                case "help":
                    System.out.println("Prints out your current character and available commands.");
                    break;
                case "take":
                    System.out.println("Type 'take' and then the full name of the item you want to take, " +
                            "i.e. 'take 500g Ground Meat'.");
                    break;
                case "go":
                    System.out.println("Type 'go' and then the direction you want to head towards, i.e. 'go east'.");
                    break;
                case "inspect":
                    System.out.println("Type 'inspect' and then the full name of the item you want to inspect and " +
                            "know more about, i.e. 'inspect 500g Ground Meat'.");
                    break;
                case "quit":
                    System.out.println("Quits and ends the game.");
                    break;
                case "check":
                    System.out.println("Type 'check' and then 'inventory' or 'section' to get a list of items " +
                            "in either your inventory or the current room, i.e. 'check section'.");
                    break;
                case "checkout":
                    System.out.println("Type 'checkout' to finish your current shopping trip, only possible " +
                            "to do in the cashier room.");
                    break;
                case "me":
                    System.out.println("Sorry, there's no help for you, you are stuck in here forever.");
                    break;
                default:
                    System.out.println("Help with what command?");
            }
        }
        else {
            System.out.println("You are out shopping as a " + player.getPlayerType().getName());
            System.out.println("| Your budget is " + player.getPlayerType().getBudgetMax() + " dkk." + "  ||  " +
                    "Your minimum calorie goal is " + player.getPlayerType().getCalorieMin() + "  |");
            System.out.println();
            System.out.println("Your command words are:");
            parser.showCommands();
            System.out.println("Tip: You can type 'help' and then the command you want to know more about. " +
                    "('help command')");
        }
    }

    /**
     * Takes care of resetting player and rooms and prints stuff to the user
     */
    private void resetGame(){
        reactToResults();
        player.deleteInventory(); // deletes all items in the inventory
        createRooms(); //creates the rooms again and fills them with items
        System.out.println(".\n" + ".\n" + ".\n" + ".\n" + ".\n" + "." );
        player.setPlayerType((ContentGenerator.getStudentPlayerType()));
        System.out.println("It is a new day, you wake up and go to the store.");
        printPlayer();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Prints messages depending on co2 and happiness
     * the more co2 the player has emitted, the hotter the world will become
     * the more negative the players happiness, the more unhappy the player will be
     * This will be reflected in the printed messages
     */
    private void reactToResults(){
        int happiness = 0;
        double co2 = 0;
        for(GameResult finishedGame : finishedGames){
            happiness += finishedGame.getHappiness();
            co2 += finishedGame.getCo2();
        }

        System.out.println("CO2: " + co2);

        if (co2 < 5) {
            System.out.println("The earth is a green and beautiful place");
        } else if (co2 < 10) {
            System.out.println("you notice your armpits are more stained than usual");
        }else if(co2 < 15){
            System.out.println("it's to hot to walk barefoot");
        }else if(co2 < 20){
            System.out.println("you can boil an egg in the ocean");
        }else if(co2 < 25){
            System.out.println("you've sold your oven, as you don't need it");
        }else {
            System.out.println("the store is on fire");
        }

        if (happiness >= 0) {
            System.out.println("you're ok...");
        }else if ( happiness > -50){
            System.out.println("you notice that you've started snapping at your friends");
        }else if(happiness > -100){
            System.out.println("you don't want to eat, even when your hungry");
        }else if(happiness > -150){
            System.out.println("you're wondering if theres a point to anything");
        }else if(happiness > -200){
            System.out.println("you've joined a fascist movement");
        }else {
            System.out.println("you have successfully toppled the government, gasoline is the only currency");
        }
    }

    /**
     * @param command Command with a second word which is the name of the room to go to
     */
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

    /**
     * Prints the items in the inventory or current room (if any)
     * Prints "no products in section/inventory" otherwise
     * @param command Command. Second word should be "inventory" or "section"
     */
    //checks if there is a second word when calling the check command and if it is either section or inventory.
    private void check(Command command) {
        String word = command.getSecondWord();
        if (word == null || !word.equalsIgnoreCase("section") && !word.equalsIgnoreCase("inventory") ){
            System.out.println("Check what? (Section or Inventory)");
            return;
        }

        if(word.equalsIgnoreCase("section")){ //checks if the second word is section
            String string = currentRoom.getItemsString();
            if (currentRoom.getItemsString() == null && !currentRoom.canCheckout()){ //checks if the room is an aisle
                System.out.println("This is an aisle you idiot! There are no products here!");
            }
            else
            System.out.println(string != null ? string : "No products in section"); //prints items from the current room
        }
        else if(word.equalsIgnoreCase("inventory")){ //checks if the second word is inventory
            String string = player.getInventoryString();
            System.out.println(string != null ? string : "No items in inventory"); //prints items from player inventory
        }
    }

    /**
     * Ends the game if the command has no second word
     * @param command Used to make sure there isn't a second word.
     * @return whether the game is to be ended
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    /**
     * prints information about the specified item
     * @param command second word should be the name of the item the player wants to inspect
     */
    //TODO:Check both Player and Room for item
    private void inspect(Command command){
        Item item = currentRoom.getItem(command.getSecondWord());
        System.out.println(item != null ? item.toString() : "item not found");
    }

    /**
     * Adds an item from the room to the players inventory
     * @param command second word should be the name of the item the player wants to pick up
     */
    private void take(Command command){
        Item item = currentRoom.getItem(command.getSecondWord());
        if(item != null){
            player.addItem(item);
            System.out.println("You picked up " + item.getName());
        }else {
            System.out.println("'" + command.getSecondWord() + "' not found in store");
        }
    }

    /**
     * Removes an item from the players inventory
     * @param command second word should be the name of the item the player wants to remove
     */
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
