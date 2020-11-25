package worldofzuul.DomainLayer.Interfaces;

import java.net.URI;
import java.util.ArrayList;

public interface IRoom {
    ArrayList<Shelf> getShelves();

    int getWidth();
    int getHeight();

    ArrayList<Warp> getWarps();

    String getBackground();
}
