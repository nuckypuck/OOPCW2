package task2;

// Suite class based on cabin
public class Suite extends Cabin {
    private final boolean hasBalcony; // Boolean for balcony

    public Suite(int number, boolean hasBalcony) {
        super(number, 4); // Occupant Limit
        this.hasBalcony = hasBalcony;
    }

    public boolean hasBalcony() {
        return hasBalcony;
    }
}


