package worldofzuul;

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

    public String toString() {
        return commandString;
    }
}
