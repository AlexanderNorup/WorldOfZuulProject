package worldofzuul;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Is responsible for getting input from the user and splitting it into a command word and a string of extra text
 */
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
       //Uses substring to cut the input into the first and second word.
       //substring works like this. The first argument is the start-character (indexed from 0), and the second argument is the length (which is also optional).
       //That means if you call .substring(2) on the String "Hello" you get the String "llo", because the new string is like the previous string, but it now starts from the 3rd character. 
       //The second argument says how many characters after the start-character the new string should continue. So calling .substring(2,2) would output "ll", because it starts from the 3rd character and reads 2 characters forward.
       //The indexOf() method on a String, will return an int which represents the character position of the first occurrence of a given String. So if you call indexOf("ll") on "Hello", you'll get 2, because "ll" first occurs in "Hello" on the 3rd character
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
