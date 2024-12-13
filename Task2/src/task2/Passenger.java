package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static task2.Task2.getValidInput;

public class Passenger {
    private final String name; // Name of the passenger
    private Cabin cabin; // The cabin assigned to the passenger

    // Initialize the passenger with a name and assigned cabin
    public Passenger(String name, Cabin cabin) {
        this.name = name;
        this.cabin = cabin;
    }

    // Get name of the passenger
    public String getName() {
        return name;
    }

    // Get the cabin of the passenger
    public Cabin getCabin() {
        return cabin;
    }

    // Setter method to update the cabin assigned to the passenger
    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    // Display information about passengers
    public static void displayPassengerInformation(List<Cruise> cruises, Scanner scanner) {
        System.out.println("\n--- Passenger Information ---");
        // Ask user to select a cruise from the list
        Cruise cruise = Cruise.selectCruise(cruises, scanner);
        if (cruise == null) return; // Exit if no cruise is selected

        List<Passenger> passengers = cruise.getPassengers(); // Get the list of passengers
        // Display the list of passengers
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            System.out.println((i + 1) + ". " + passenger.getName()); // Display passenger names
        }
        
        System.out.print("Select a passenger: ");
        // Get a valid input from the user
        int choice = getValidInput(scanner);
        if (choice == -1 || choice < 1 || choice > passengers.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Exit if invalid
        }

        // Retrieve the selected passenger
        Passenger passenger = passengers.get(choice - 1);
        Cabin cabin = passenger.getCabin(); // Get the cabin assigned to the passenger
        
        // Display cabin information (Suite or Standard) along with features (Balcony or Sea View)
        System.out.print("Cabin: " + cabin.getNumber() +
            (cabin instanceof Suite ? " (Suite) " +
                (((Suite) cabin).hasBalcony() ? "(Has Balcony)" : "(No Balcony)") :
            " (Standard) " +
                (((StandardCabin) cabin).hasSeaView() ? "(Has Sea View)" : "(No Sea View)")));
        System.out.println();

        // Display the names of passengers sharing the same cabin
        List<String> cabinMates = new ArrayList<>();
        for (Passenger otherPassenger : passengers) {
            if (otherPassenger != passenger && otherPassenger.getCabin() == cabin) {
                cabinMates.add(otherPassenger.getName()); // Add other passengers in the same cabin
            }
        }

        // Display if the passenger is sharing the cabin or not
        if (cabinMates.isEmpty()) {
            System.out.println("This passenger is not sharing the cabin with anyone.");
        } else {
            cabinMates.sort(String::compareTo); // Sort cabin mates alphabetically
            System.out.println("Sharing cabin with: " + String.join(", ", cabinMates)); // Display cabin mates
        }

        // Display all booked excursions for the passenger
        List<String> bookedExcursions = new ArrayList<>();
        for (Excursion excursion : cruise.getExcursions()) {
            if (excursion.getPassengers().contains(passenger)) {
                bookedExcursions.add(excursion.getPort().getName() + " on " + excursion.getDayOfWeek()); // Add excursion details
            }
        }

        // Display if the passenger has any booked excursions
        if (bookedExcursions.isEmpty()) {
            System.out.println("This passenger is not currently booked on any excursions.");
        } else {
            System.out.println("Excursions booked: " + String.join("; ", bookedExcursions)); // Display booked excursions
        }
    }
}
