package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static task2.Task2.getValidInput;

public abstract class Cabin {
    private final int number; // Cabin number
    private final int passengerLimit; // Maximum number of passengers allowed in this cabin

    // Initialize the cabin with a number and passenger limit
    public Cabin(int number, int passengerLimit) {
        this.number = number;
        this.passengerLimit = passengerLimit;
    }

    // Get the cabin number
    public int getNumber() {
        return number;
    }

    // Get the passenger limit for the cabin
    public int getPassengerLimit() {
        return passengerLimit;
    }
    
    // Allow a user to change a passenger's cabin
    public static void changePassengerCabin(List<Cruise> cruises, Scanner scanner) {
        System.out.println("\n--- Change Passenger Cabin ---");
        
        // Prompt the user to select a cruise
        Cruise cruise = Cruise.selectCruise(cruises, scanner);
        if (cruise == null) return; // Exit if no cruise is selected

        List<Passenger> passengers = cruise.getPassengers(); // Get the list of passengers on the cruise
        // Display the list of passengers
        for (int i = 0; i < passengers.size(); i++) {
            System.out.println((i + 1) + ". " + passengers.get(i).getName());
        }
        System.out.print("Select a passenger: ");

        // Get the valid input for passenger selection
        int passengerIndex = getValidInput(scanner);
        if (passengerIndex == -1 || passengerIndex < 1 || passengerIndex > passengers.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Exit if the selection is invalid
        }

        // Get the selected passenger and their current cabin
        Passenger passenger = passengers.get(passengerIndex - 1);
        Cabin currentCabin = passenger.getCabin();
        List<Cabin> availableCabins = new ArrayList<>(); // List to store available cabins

        // Check for available cabins based on passenger limit
        for (Cabin cabin : cruise.getShip().getCabins()) {
            if (cabin != currentCabin) {
                // Count the number of passengers already in the cabin
                int currentOccupancy = (int) cruise.getPassengers().stream()
                    .filter(p -> p.getCabin() == cabin)
                    .count();
                if (currentOccupancy < cabin.getPassengerLimit()) {
                    availableCabins.add(cabin); // Add the cabin if it has space available
                }
            }
        }

        // If no available cabins are found, prompt and return
        if (availableCabins.isEmpty()) {
            System.out.println("No other cabins are available for reassignment.");
            return;
        }

        // Display the current cabin and its details (Suite or Standard)
        System.out.println("\nCurrent Cabin: " + currentCabin.getNumber() +
                (currentCabin instanceof Suite ? " (Suite) " +
                    (((Suite) currentCabin).hasBalcony() ? "(Has Balcony)" : "(No Balcony)") :
                " (Standard) " +
                    (((StandardCabin) currentCabin).hasSeaView() ? "(Has Sea View)" : "(No Sea View)")));
        
        // Display available cabins for reassignment
        System.out.println("Available Cabins:");
        System.out.println("0. Cancel and return to the main menu");
        for (int i = 0; i < availableCabins.size(); i++) {
            Cabin cabin = availableCabins.get(i);
            System.out.println((i + 1) + ". Cabin " + cabin.getNumber() +
                (cabin instanceof Suite ? " (Suite) " +
                    (((Suite) cabin).hasBalcony() ? "(Has Balcony)" : "(No Balcony)") :
                " (Standard) " +
                    (((StandardCabin) cabin).hasSeaView() ? "(Has Sea View)" : "(No Sea View)")));
        }
        System.out.print("Select a new cabin: ");

        // Get the valid input for cabin selection
        int cabinIndex = getValidInput(scanner);
        if (cabinIndex == 0) {
            System.out.println("Canceled. Returning to the main menu.");
            return; // Exit if the user cancels the cabin change
        }
        if (cabinIndex == -1 || cabinIndex < 1 || cabinIndex > availableCabins.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Exit if the selection is invalid
        }

        // Get the selected cabin
        Cabin selectedCabin = availableCabins.get(cabinIndex - 1);

        // Change the passenger's cabin to the selected one
        passenger.setCabin(selectedCabin);
        System.out.println("Cabin changed successfully. " + passenger.getName() +
                " is now in Cabin " + selectedCabin.getNumber() +
                (selectedCabin instanceof Suite ? " (Suite) " +
                    (((Suite) selectedCabin).hasBalcony() ? "(Has Balcony)" : "(No Balcony)") :
                " (Standard) " +
                    (((StandardCabin) selectedCabin).hasSeaView() ? "(Has Sea View)" : "(No Sea View)")));
    }
}
