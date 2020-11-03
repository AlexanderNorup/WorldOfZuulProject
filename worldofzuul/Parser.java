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
        String word1;
        String word2;

        System.out.print("> ");

        inputLine = reader.nextLine();

        if(inputLine.contains(" ")){
            word1 = inputLine.substring(0, inputLine.indexOf(' '));
            word2 = inputLine.substring(inputLine.indexOf(' ')+1);
        }else {
            word1 = inputLine;
            word2 = null;
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    public void showCommands() {
        commands.showAll();
    }
}
