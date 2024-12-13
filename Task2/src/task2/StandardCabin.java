package task2;

public class StandardCabin extends Cabin {
    private final boolean hasSeaView; // Boolean for seaview

    public StandardCabin(int number, boolean hasSeaView) {
        super(number, 6); // Occupant Limit
        this.hasSeaView = hasSeaView;
    }

    public boolean hasSeaView() {
        return hasSeaView;
    }
}

