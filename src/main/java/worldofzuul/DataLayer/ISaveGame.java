package worldofzuul.DataLayer;

import java.util.ArrayList;

public interface ISaveGame {
    void save(ArrayList<GameResultData> gameResults) throws SaveGameException;
    ArrayList<GameResultData> load() throws SaveGameException;
    void delete();
}
