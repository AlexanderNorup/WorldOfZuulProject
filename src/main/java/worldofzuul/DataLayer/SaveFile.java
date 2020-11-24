package worldofzuul.DataLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import worldofzuul.DomainLayer.ContentGenerator;
import worldofzuul.DomainLayer.GameResult;
import worldofzuul.DomainLayer.PlayerType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for taking care of reading from and writing to a JSON file
 * The data is an Arraylist of GameResults
 * A GameResult contains a PlayerType - only the name of this is saved to the file.
 */
public class SaveFile {
    private static final String path = "./saveFile.json";

    private static final String CO2_STRING = "co2";
    private static final String HAPPINESS_STRING = "happiness";
    private static final String PLAYERTYPE_STRING = "playerType";

    /**
     * Saves the given Arraylist to a JSON file
     * Will create the JSON file if it doesn't already exist
     * Throws a FileNotFoundException if the path is somehow broken,
     * but I don't se how that would happen since the path is relative
     * I don't se how a JSONException would be thrown either
     * @param gameResults The data to be saved
     * @throws FileNotFoundException
     * @throws JSONException
     */
    public static void save(ArrayList<GameResult> gameResults) throws FileNotFoundException, JSONException {
        JSONArray arr = new JSONArray();
        for (GameResult g : gameResults) {
            JSONObject o = new JSONObject();

            //The data is put into the file as key-value pairs
            o.put(CO2_STRING, g.getCo2());
            o.put(HAPPINESS_STRING, g.getHappiness());
            o.put(PLAYERTYPE_STRING, g.getPlayerType().getName());

            arr.put(o);
        }
        PrintWriter printWriter = new PrintWriter(path);
        printWriter.println(arr.toString());
        printWriter.close();
    }

    /**
     * Loads data from a saved file
     * If there's no saved file, the method throws a FileNotFoundException
     * Would be appropriate for the calling method to create an empty ArrayList in this case
     *
     * @return The ArrayList of Gameresults last saved to the file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws JSONException
     */
    public static ArrayList<GameResult> load() throws FileNotFoundException, IOException, JSONException {
        ArrayList<GameResult> toReturn = new ArrayList<>();

        File file = new File(path);
        Scanner s = new Scanner(file);

        // Throws an IOException if the file is empty
        if (s.hasNextLine()) {

            // Reads a string from the scanner and converts it into a jsonArray
            // Throws a JSONException, if the contents of the JSON file is not an array
            String jsonString = s.nextLine();
            JSONArray arr = new JSONArray(jsonString);

            double co2;
            double happiness;
            PlayerType playerType;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = (JSONObject) arr.get(i);
                co2 = o.getDouble(CO2_STRING);
                happiness = (double) o.getDouble(HAPPINESS_STRING);
                playerType = ContentGenerator.getPlayerTypeByName(o.getString(PLAYERTYPE_STRING));
                toReturn.add(new GameResult(co2, happiness, playerType));
            }

        } else {
            throw new IOException("Seems like the saveFile.json file exists but is empty");
        }

        return toReturn;
    }
}
