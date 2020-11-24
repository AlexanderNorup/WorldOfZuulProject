package worldofzuul.DataLayer;


public class SaveGameException extends Exception {
    public SaveGameException(String errorMessage) {
        super(errorMessage);
    }

    public SaveGameException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
