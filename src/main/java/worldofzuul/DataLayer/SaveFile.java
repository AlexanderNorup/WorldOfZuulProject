package worldofzuul.DataLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class SaveFile implements ISaveGame {
    private String path;

    private static final String CO2_STRING = "co2";
    private static final String HAPPINESS_STRING = "happiness";
    private static final String PLAYERTYPE_STRING = "playerType";

    public SaveFile(String path){
        this.path = path;
    }

    /**
     * Saves the given Arraylist to a JSON file
     * Will create the JSON file if it doesn't already exist
     * @param gameResults
     * @throws SaveGameException JSON or FileNotFound-Exceptions will be chained to a SaveGameException and thrown
     */
    public void save(ArrayList<GameResultData> gameResults) throws SaveGameException {
        JSONArray arr = new JSONArray();
        for (GameResultData g : gameResults) {
            JSONObject o = new JSONObject();

            //The data is put into the file as key-value pairs
            try {
                o.put(CO2_STRING, g.getCo2());
                o.put(HAPPINESS_STRING, g.getHappiness());
                o.put(PLAYERTYPE_STRING, g.getPlayerTypeName());
            } catch (JSONException e){
                throw new SaveGameException("JSON Exception when saving game", e);
            }

            arr.put(o);
        }
        try {
            PrintWriter printWriter = new PrintWriter(path);
            printWriter.println(arr.toString());
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new SaveGameException("FileNotFoundException when saving game", e);
        }
    }

    /**
     * Loads data from a saved file
     * @return
     * @throws SaveGameException JSON, IO or FileNotFound-Exceptions will be chained to a SaveGameException and thrown
     */
    public ArrayList<GameResultData> load() throws SaveGameException{
        ArrayList<GameResultData> toReturn = new ArrayList<>();

        File file = new File(path);

        Scanner s = null;
        try {
            s = new Scanner(file);

            // Throws an IOException if the file is empty
            if (s.hasNextLine()) {

                // Reads a string from the scanner and converts it into a jsonArray
                // Throws a JSONException, if the contents of the JSON file is not an array
                String jsonString = s.nextLine();
                JSONArray arr = new JSONArray(jsonString);

                double co2;
                double happiness;
                String playerTypeName;
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = (JSONObject) arr.get(i);
                    co2 = o.getDouble(CO2_STRING);
                    happiness = o.getDouble(HAPPINESS_STRING);
                    playerTypeName = o.getString(PLAYERTYPE_STRING);
                    toReturn.add(new GameResultData(co2, happiness, playerTypeName));
                }

            } else {
                //Is caught immediately an chained with a SaveGameException
                throw new IOException("Seems like the saveFile.json file exists but is empty");
            }
        } catch (IOException e) {
            throw new SaveGameException("IOException when loading game", e);
        } catch (JSONException e) {
            throw new SaveGameException("JSONException when loading game", e);
        } finally {
            if (s != null){
                s.close();
            }
        }

        return toReturn;
    }

    public void delete() {
        File file = new File(path);
        file.delete();
    }
}
