package worldofzuul;

import java.util.HashMap;

/**
 * Contains information about valid commands and can turn strings in to the valid Command type.
 */

public class CommandWords {
    private HashMap<String, CommandWord> validCommands;

    public CommandWords() {
        validCommands = new HashMap<String, CommandWord>();
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);

        return command != null ? command: CommandWord.UNKNOWN;
    }

    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    public void showAll() {
        for (String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
