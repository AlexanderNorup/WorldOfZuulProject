package worldofzuul.Commandhandling;

import worldofzuul.Commandhandling.CommandWord;

import java.util.HashMap;

/**
 * Contains information about valid commands and can turn strings in to the valid Command type.
 */

public class CommandWords {
    private final HashMap<String, CommandWord> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * returns the corresponding command of the String value
     * passed in the argument.
     * @param commandWord string of the desired command
     * @return returns CommandWord object if the hashmap of valid
     * commands contains a key that matches the passed String.
     * if not the hashmap will return null, which will make the
     * method return CommandWord.UNKNOWN as a result of the ternary
     * operation.
     */
    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);

        return command != null ? command: CommandWord.UNKNOWN;
    }

    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    /**
     prints all valid command word Strings.
     */
    public void showAll() {
        for (String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
