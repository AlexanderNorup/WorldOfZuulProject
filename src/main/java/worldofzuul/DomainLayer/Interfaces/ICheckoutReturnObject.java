package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public interface ICheckoutReturnObject {

    boolean didCheckout();

    boolean isGameOver();

    ArrayList<String> getReturnString();
}
