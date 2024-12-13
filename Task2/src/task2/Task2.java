package task2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        List<Cruise> cruises = initializeData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCruise Management System");
            System.out.println("1. Display Cruise Information"); // Display Cruise Info (All cruises and their details)
            System.out.println("2. Display Excursion Information"); // Display Excursion Info (All excursions related to a cruise)
            System.out.println("3. Display Passenger Information"); // Display passenger Info (Their cabin, excursions, etc)
            System.out.println("4. Book Excursion"); // Book a passenger onto an Excursion
            System.out.println("5. Change Passenger Cabin"); // Switch a passenger into another cabin if avaliable
            System.out.println("6. Exit"); // Exit Program
            System.out.print("Enter your choice: ");

            int choice = getValidInput(scanner);
            if (choice == -1) continue;

            // Switchcase to related function
            switch (choice) {
                case 1:
                    Cruise.displayCruiseInformation(cruises, scanner);
                    break;
                case 2:
                    Excursion.displayExcursionInformation(cruises, scanner);
                    break;
                case 3:
                    Passenger.displayPassengerInformation(cruises, scanner);
                    break;
                case 4:
                    Excursion.bookExcursion(cruises, scanner);
                    break;
                case 5:
                    Cabin.changePassengerCabin(cruises, scanner);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }
        }
    }

    // Input Validation
    public static int getValidInput(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
            return -1; // Indicate invalid input
        }
    }

    // Dummy Data. Features 10 Cruises, 3 Excursions per cruise, some fully occupied rooms and excursions and some partially occupied rooms and excursions for testing.
    private static List<Cruise> initializeData() {

    // Ports
    Port portBarcelona = new Port("Barcelona");
    Port portNaples = new Port("Naples");
    Port portSantorini = new Port("Santorini");
    Port portMykonos = new Port("Mykonos");
    Port portAthens = new Port("Athens");
    Port portVenice = new Port("Venice");
    Port portDubrovnik = new Port("Dubrovnik");
    Port portMalta = new Port("Malta");
    Port portPalermo = new Port("Palermo");

    List<Cruise> cruises = new ArrayList<>();

// Ship 1: Paragon
Ship ship1 = new Ship("Paragon"); // Ship Name
ship1.addCabin(new Suite(101, true)); // Cabin (Suite) (Boolean, True for has balcony).
ship1.addCabin(new StandardCabin(201, false)); // Cabin (Standard) (Boolean, False for no sea view).
Cruise cruise1 = new Cruise(ship1); // Create as a Cruise Object
// Fully book Suite 101 (Add Passengers)
cruise1.addPassenger(new Passenger("Leonardo DiCaprio", ship1.getCabins().get(0)));
cruise1.addPassenger(new Passenger("Scarlett Johansson", ship1.getCabins().get(0)));
cruise1.addPassenger(new Passenger("Tom Hardy", ship1.getCabins().get(0)));
cruise1.addPassenger(new Passenger("Emma Stone", ship1.getCabins().get(0)));
// Partially book Standard 201
cruise1.addPassenger(new Passenger("Chris Evans", ship1.getCabins().get(1)));

// Set Excursions, Day, Capacity (Add to cruise1)
cruise1.addExcursion(new Excursion(portBarcelona, "Monday", 5)); 
cruise1.addExcursion(new Excursion(portNaples, "Wednesday", 3)); 
cruise1.addExcursion(new Excursion(portSantorini, "Friday", 4)); 
cruises.add(cruise1);

// Ship 2: Young Lion
Ship ship2 = new Ship("Young Lion");
ship2.addCabin(new Suite(101, true)); // Partially booked
ship2.addCabin(new StandardCabin(201, false)); // Fully booked
Cruise cruise2 = new Cruise(ship2);
cruise2.addPassenger(new Passenger("Emma Watson", ship2.getCabins().get(0)));
cruise2.addPassenger(new Passenger("Daniel Radcliffe", ship2.getCabins().get(0)));
cruise2.addPassenger(new Passenger("Rupert Grint", ship2.getCabins().get(1)));
cruise2.addPassenger(new Passenger("Alan Rickman", ship2.getCabins().get(1)));
cruise2.addPassenger(new Passenger("Maggie Smith", ship2.getCabins().get(1)));
cruise2.addPassenger(new Passenger("Helena Bonham Carter", ship2.getCabins().get(1)));
cruise2.addPassenger(new Passenger("Michael Gambon", ship2.getCabins().get(1)));
cruise2.addPassenger(new Passenger("Robbie Coltrane", ship2.getCabins().get(1)));

// Create excursions
Excursion excursion1 = new Excursion(new Port("Santorini"), "Thursday", 4); // Fully booked for testing
excursion1.addPassenger(cruise2.getPassengers().get(0)); // Emma Watson
excursion1.addPassenger(cruise2.getPassengers().get(1)); // Daniel Radcliffe
excursion1.addPassenger(cruise2.getPassengers().get(2)); // Rupert Grint
excursion1.addPassenger(cruise2.getPassengers().get(3)); // Alan Rickman

Excursion excursion2 = new Excursion(new Port("Athens"), "Friday", 6); // Partially booked
excursion2.addPassenger(cruise2.getPassengers().get(4)); // Maggie Smith
excursion2.addPassenger(cruise2.getPassengers().get(5)); // Helena Bonham Carter

Excursion excursion3 = new Excursion(new Port("Venice"), "Monday", 3); // 1 Space for testing
excursion3.addPassenger(cruise2.getPassengers().get(6)); // Michael Gambon
excursion3.addPassenger(cruise2.getPassengers().get(7)); // Robbie Coltrane

// Add excursions to cruise
cruise2.addExcursion(excursion1);
cruise2.addExcursion(excursion2);
cruise2.addExcursion(excursion3);

// Add cruise to list
cruises.add(cruise2);


// Ship 3: Castilian
Ship ship3 = new Ship("Castilian");
ship3.addCabin(new Suite(101, true)); 
ship3.addCabin(new StandardCabin(201, false)); 
Cruise cruise3 = new Cruise(ship3);
cruise3.addPassenger(new Passenger("Chris Hemsworth", ship3.getCabins().get(0)));
cruise3.addPassenger(new Passenger("Gal Gadot", ship3.getCabins().get(0)));
cruise3.addPassenger(new Passenger("Zendaya", ship3.getCabins().get(0)));
cruise3.addPassenger(new Passenger("Robert Downey Jr.", ship3.getCabins().get(0)));
cruise3.addPassenger(new Passenger("Tom Hiddleston", ship3.getCabins().get(1)));

cruise3.addExcursion(new Excursion(portNaples, "Tuesday", 4)); 
cruise3.addExcursion(new Excursion(portSantorini, "Thursday", 8)); 
cruise3.addExcursion(new Excursion(portDubrovnik, "Saturday", 6)); 
cruises.add(cruise3);

// Ship 4: Downley
Ship ship4 = new Ship("Downley");
ship4.addCabin(new Suite(101, false));
ship4.addCabin(new StandardCabin(201, true)); 
Cruise cruise4 = new Cruise(ship4);
cruise4.addPassenger(new Passenger("Tom Holland", ship4.getCabins().get(0)));
cruise4.addPassenger(new Passenger("Andrew Garfield", ship4.getCabins().get(1)));
cruise4.addExcursion(new Excursion(portBarcelona, "Monday", 2)); 
cruise4.addExcursion(new Excursion(portMykonos, "Friday", 10)); 
cruise4.addExcursion(new Excursion(portMalta, "Wednesday", 4)); 
cruises.add(cruise4);

// Ship 5: Avon Vale
Ship ship5 = new Ship("Avon Vale");
ship5.addCabin(new Suite(101, true)); 
ship5.addCabin(new StandardCabin(202, false)); 
Cruise cruise5 = new Cruise(ship5);
cruise5.addPassenger(new Passenger("Ryan Reynolds", ship5.getCabins().get(0)));
cruise5.addPassenger(new Passenger("Blake Lively", ship5.getCabins().get(0)));
cruise5.addPassenger(new Passenger("Hugh Jackman", ship5.getCabins().get(1)));

cruise5.addExcursion(new Excursion(portAthens, "Thursday", 5)); 
cruise5.addExcursion(new Excursion(portNaples, "Wednesday", 4)); 
cruise5.addExcursion(new Excursion(portPalermo, "Monday", 6)); 

// Ship 6: Cholmondeley
Ship ship6 = new Ship("Cholmondeley");
ship6.addCabin(new Suite(101, false)); 
ship6.addCabin(new StandardCabin(202, true)); 
Cruise cruise6 = new Cruise(ship6);
cruise6.addPassenger(new Passenger("Chris Pratt", ship6.getCabins().get(0)));
cruise6.addPassenger(new Passenger("Zoe Saldana", ship6.getCabins().get(1)));
cruise6.addExcursion(new Excursion(portSantorini, "Tuesday", 6)); 
cruise6.addExcursion(new Excursion(portBarcelona, "Thursday", 3)); 
cruise6.addExcursion(new Excursion(portVenice, "Saturday", 5)); 
cruises.add(cruise6);

// Ship 7: Raglan
Ship ship7 = new Ship("Raglan");
ship7.addCabin(new Suite(101, true)); 
ship7.addCabin(new StandardCabin(202, false)); 
Cruise cruise7 = new Cruise(ship7);
cruise7.addPassenger(new Passenger("Benedict Cumberbatch", ship7.getCabins().get(0)));
cruise7.addPassenger(new Passenger("Tom Hiddleston", ship7.getCabins().get(0)));

cruise7.addExcursion(new Excursion(portMykonos, "Friday", 5));
cruise7.addExcursion(new Excursion(portAthens, "Wednesday", 4));
cruise7.addExcursion(new Excursion(portDubrovnik, "Monday", 6));
cruises.add(cruise7);

// Ship 8: The Dorset
Ship ship8 = new Ship("The Dorset");
ship8.addCabin(new Suite(101, false));
ship8.addCabin(new StandardCabin(202, true));
Cruise cruise8 = new Cruise(ship8);
cruise8.addPassenger(new Passenger("Patrick Stewart", ship8.getCabins().get(0)));
cruise8.addExcursion(new Excursion(portNaples, "Thursday", 2));
cruise8.addExcursion(new Excursion(portSantorini, "Monday", 3));
cruise8.addExcursion(new Excursion(portMalta, "Wednesday", 4));
cruises.add(cruise8);

// Ship 9: Ready
Ship ship9 = new Ship("Ready");
ship9.addCabin(new Suite(101, true));
ship9.addCabin(new StandardCabin(202, false));
Cruise cruise9 = new Cruise(ship9);
cruise9.addPassenger(new Passenger("Matt Damon", ship9.getCabins().get(0)));
cruise9.addPassenger(new Passenger("Ben Affleck", ship9.getCabins().get(1)));
cruise9.addExcursion(new Excursion(portMykonos, "Friday", 8));
cruise9.addExcursion(new Excursion(portAthens, "Tuesday", 7));
cruise9.addExcursion(new Excursion(portPalermo, "Monday", 5));
cruises.add(cruise9);

// Ship 10: Moresby
Ship ship10 = new Ship("Moresby");
ship10.addCabin(new Suite(101, false));
ship10.addCabin(new StandardCabin(202, true));
Cruise cruise10 = new Cruise(ship10);
cruise10.addPassenger(new Passenger("Henry Cavill", ship10.getCabins().get(0)));
cruise10.addPassenger(new Passenger("Jason Momoa", ship10.getCabins().get(1)));
cruise10.addExcursion(new Excursion(portBarcelona, "Monday", 6));
cruise10.addExcursion(new Excursion(portSantorini, "Thursday", 5));
cruise10.addExcursion(new Excursion(portDubrovnik, "Sunday", 4));
cruises.add(cruise10);

return cruises;

}

}



