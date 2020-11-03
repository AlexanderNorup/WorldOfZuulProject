package worldofzuul;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser {
    private final CommandWords commands;
    private final Scanner reader;

    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine;

        System.out.print("> ");

        inputLine = reader.nextLine();

        String word1 = inputLine.substring(0, inputLine.indexOf(' '));
        String word2 = inputLine.substring(inputLine.indexOf(' ')+1);

        System.out.println("Word1: " + word1 + " word2: " + word2);

        return new Command(commands.getCommandWord(word1), word2);
    }

    public void showCommands() {
        commands.showAll();
    }
}
