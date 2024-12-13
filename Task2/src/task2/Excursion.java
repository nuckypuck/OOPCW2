package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static task2.Task2.getValidInput;

public class Excursion {
    private final Port port; // The port of the excursion
    private final String dayOfWeek; // The day of the week the excursion occurs
    private final int passengerLimit; // The maximum number of passengers for the excursion
    private final List<Passenger> passengers; // List of passengers booked for the excursion

    // Construct an excursion with a port, day of the week, and passenger limit
    public Excursion(Port port, String dayOfWeek, int passengerLimit) {
        this.port = port;
        this.dayOfWeek = dayOfWeek;
        this.passengerLimit = passengerLimit;
        this.passengers = new ArrayList<>(); // Initialize an empty list of passengers
    }

    // Get the port of the excursion
    public Port getPort() {
        return port;
    }

    // Get the day of the week the excursion occurs
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    // Get the passenger limit for the excursion
    public int getPassengerLimit() {
        return passengerLimit;
    }

    // Get the list of passengers currently booked on the excursion
    public List<Passenger> getPassengers() {
        return passengers;
    }

    // Add a passenger to the excursion if there's space available
    public boolean addPassenger(Passenger passenger) {
        if (passengers.size() < passengerLimit) { // Check if the excursion has space
            passengers.add(passenger); // Add passenger to the excursion
            return true; // Successfully added
        }
        return false; // No space available, failed to add passenger
    }

    // Display information about excursions for a selected cruise
    public static void displayExcursionInformation(List<Cruise> cruises, Scanner scanner) {
        System.out.println("\n--- Excursion Information ---");
        // Prompt user to select a cruise
        Cruise cruise = Cruise.selectCruise(cruises, scanner);
        if (cruise == null) return; // Exit if no cruise is selected

        // Display the list of excursions for the selected cruise
        for (int i = 0; i < cruise.getExcursions().size(); i++) {
            Excursion excursion = cruise.getExcursions().get(i);
            System.out.println((i + 1) + ". Excursion to " + excursion.getPort().getName() +
                    " on " + excursion.getDayOfWeek());
        }
        System.out.print("Select an excursion: ");

        int choice = getValidInput(scanner); // Get user input for excursion selection
        if (choice == -1 || choice < 1 || choice > cruise.getExcursions().size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Exit if invalid
        }

        // Retrieve the selected excursion
        Excursion excursion = cruise.getExcursions().get(choice - 1);
        // Display detailed information about the selected excursion
        System.out.println("Excursion to " + excursion.getPort().getName() +
                " on " + excursion.getDayOfWeek());
        System.out.println("Passengers:");
        // List all passengers currently booked on this excursion
        for (Passenger passenger : excursion.getPassengers()) {
            System.out.println(" - " + passenger.getName());
        }
    }

    // Book a passenger onto an available excursion for a selected cruise
    public static void bookExcursion(List<Cruise> cruises, Scanner scanner) {
        System.out.println("\n--- Book Excursion ---");
        // Prompt user to select a cruise
        Cruise cruise = Cruise.selectCruise(cruises, scanner);
        if (cruise == null) return; // Exit if no cruise is selected

        // List all passengers of the selected cruise
        List<Passenger> passengers = cruise.getPassengers();
        for (int i = 0; i < passengers.size(); i++) {
            System.out.println((i + 1) + ". " + passengers.get(i).getName());
        }
        System.out.print("Select a passenger: ");

        int passengerIndex = getValidInput(scanner); // Get user input for passenger selection
        if (passengerIndex == -1 || passengerIndex < 1 || passengerIndex > passengers.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Exit if invalid
        }

        Passenger passenger = passengers.get(passengerIndex - 1);
        List<Excursion> availableExcursions = new ArrayList<>();
        // Check which excursions are available for the selected passenger (not yet booked)
        for (Excursion excursion : cruise.getExcursions()) {
            if (!excursion.getPassengers().contains(passenger)) {
                availableExcursions.add(excursion); // Add to available excursions if passenger isn't booked
            }
        }

        // If there are no available excursions, notify the user
        if (availableExcursions.isEmpty()) {
            System.out.println("This passenger is already booked on all available excursions.");
            return;
        }

        // Display available excursions with the remaining spaces
        System.out.println("\nAvailable Excursions:");
        System.out.println("0. Cancel and return to the main menu");
        for (int i = 0; i < availableExcursions.size(); i++) {
            Excursion excursion = availableExcursions.get(i);
            System.out.println((i + 1) + ". Excursion to " + excursion.getPort().getName() +
                    " on " + excursion.getDayOfWeek() +
                    " (" + (excursion.getPassengerLimit() - excursion.getPassengers().size()) + " spaces available)");
        }
        System.out.print("Select an excursion: ");

        int excursionIndex = getValidInput(scanner); // Get user input for excursion selection
        if (excursionIndex == 0) {
            System.out.println("Canceled. Returning to the main menu.");
            return; // Exit if the user cancels
        }
        if (excursionIndex == -1 || excursionIndex < 1 || excursionIndex > availableExcursions.size()) {
            System.out.println("Invalid selection. Returning to the main menu.");
            return; // Exit if invalid
        }

        // Retrieve the selected excursion
        Excursion selectedExcursion = availableExcursions.get(excursionIndex - 1);
        // Attempt to add the passenger to the selected excursion
        if (selectedExcursion.addPassenger(passenger)) {
            System.out.println("Passenger " + passenger.getName() + " successfully booked for the excursion.");
        } else {
            System.out.println("Failed to book excursion. It may be fully booked.");
        }
    }
}
