package task2;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final String name; // Name of the ship
    private final List<Cabin> cabins; // List to store cabisn on the ship

    // Construct initilize the ship with its name and empty cabin list
    public Ship(String name) {
        this.name = name;
        this.cabins = new ArrayList<>();
    }

    // Get name of ship
    public String getName() {
        return name;
    }

    // Get list off cabins
    public List<Cabin> getCabins() {
        return cabins;
    }

    // Add cabin to ship's list of cabins
    public void addCabin(Cabin cabin) {
        cabins.add(cabin);
    }
}
