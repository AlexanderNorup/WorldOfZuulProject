package worldofzuul.DomainLayer.Commandhandling;

/**
 * Contains the valid command type and their corresponding String value.
 */

public enum CommandWord {
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"),
    TAKE("take"), DROP("drop"), INSPECT("inspect"),
    CHECK("check"), CHECKOUT("checkout");

    private final String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    /**
     * overrides toString method to return the variable
     * commandString when used on object of the CommandWord
     * type.
     * @return commandString
     */
    @Override
    public String toString() {
        return commandString;
    }
}
