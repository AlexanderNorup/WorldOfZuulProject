package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.ICheckoutReturnObject;

import java.util.ArrayList;

public class CheckoutReturnObject implements ICheckoutReturnObject {

    private boolean didCheckout = false;
    private boolean gameOver = false;
    private ArrayList<String> returnString = new ArrayList<>();

    public CheckoutReturnObject() {}

    @Override
    public boolean didCheckout() {
        return didCheckout;
    }

    public void setDidCheckout(boolean didCheckout) {
        this.didCheckout = didCheckout;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ArrayList<String> getReturnString() {
        return returnString;
    }

    public void addReturnStrings(ArrayList<String> returnStrings) {
        this.returnString.addAll(returnStrings);
    }

    public void addReturnStrings(String returnString) {
        this.returnString.add(returnString);
    }

}
