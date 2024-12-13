package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static task2.Task2.getValidInput;

public class Cruise {
    private final Ship ship; // The ship associated with this cruise
    private final List<Excursion> excursions; // List of excursions available on this cruise
    private final List<Passenger> passengers; // List of passengers booked on this cruise

    // Initialize the cruise with a ship
    public Cruise(Ship ship) {
        this.ship = ship; // Set the ship for this cruise
        this.excursions = new ArrayList<>(); // Initialize the list of excursions as empty
        this.passengers = new ArrayList<>(); // Initialize the list of passengers as empty
    }

    // Get the ship associated with this cruise
    public Ship getShip() {
        return ship;
    }

    // Get the list of excursions for this cruise
    public List<Excursion> getExcursions() {
        return excursions;
    }

    // Get the list of passengers for this cruise
    public List<Passenger> getPassengers() {
        return passengers;
    }

    // Add a passenger to the cruise
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger); // Add the passenger to the list
    }

    // Add an excursion to the cruise
    public void addExcursion(Excursion excursion) {
        excursions.add(excursion); // Add the excursion to the list
    }

    // Select a cruise from the list of cruises
    public static Cruise selectCruise(List<Cruise> cruises, Scanner scanner) {
        // Display a list of available cruises (by ship name)
        for (int i = 0; i < cruises.size(); i++) {
            System.out.println((i + 1) + ". " + cruises.get(i).getShip().getName());
        }
        System.out.print("Select a cruise: ");

        // Get a valid input from the user
        int choice = getValidInput(scanner);
        if (choice == -1 || choice < 1 || choice > cruises.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return null; // Return null if the input is invalid
        }
        return cruises.get(choice - 1); // Return the selected cruise
    }

    // Display information about a selected cruise
    public static void displayCruiseInformation(List<Cruise> cruises, Scanner scanner) {
        System.out.println("\n--- Cruise Information ---");
        // Display all available cruises with ship names
        for (int i = 0; i < cruises.size(); i++) {
            System.out.println((i + 1) + ". " + cruises.get(i).getShip().getName());
        }
        System.out.print("Select a cruise: ");

        // Get a valid input from the user
        int choice = Task2.getValidInput(scanner);
        if (choice == -1 || choice < 1 || choice > cruises.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Return if the selection is invalid
        }

        // Get the selected cruise
        Cruise cruise = cruises.get(choice - 1);
        // Display the selected cruise's details
        System.out.println("Cruise: " + cruise.getShip().getName());
        System.out.println("Passengers: " + cruise.getPassengers().size()); // Number of passengers on the cruise
        // Display all the excursions for the selected cruise
        for (Excursion excursion : cruise.getExcursions()) {
            System.out.println("Excursion to " + excursion.getPort().getName() + " on " +
                    excursion.getDayOfWeek() + " (" +
                    (excursion.getPassengerLimit() - excursion.getPassengers().size()) + " spaces available)");
        }
    }
}
