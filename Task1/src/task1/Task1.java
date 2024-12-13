package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Task1 {
    private static final List<Superbowl> superbowls = new ArrayList<>(); //Stores Superbowl Data

    public static void main(String[] args) {
    loadSuperbowlData("superbowls.txt"); //Load data from txt file

    try (Scanner scanner = new Scanner(System.in)) {
        int choice = -1; // Initialize with a default invalid value
        do {
            System.out.println("----------------------------");
            System.out.println("NFL Superbowls menu       ");
            System.out.println("----------------------------");
            System.out.println("List .......................1");
            System.out.println("Select .....................2");
            System.out.println("Search .....................3");
            System.out.println("Exit .......................0");
            System.out.print("Enter choice:> ");

            // Handle invalid input
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                // Menu Choices
                switch (choice) {
                    case 1:
                        listSuperbowl(scanner); // List
                        break;
                    case 2:
                        selectSuperbowl(scanner); // Select
                        break;
                    case 3:
                        searchSuperbowl(scanner); // Search
                        break;
                    case 0:
                        System.out.println("Exiting..."); // Exit
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 0 and 3.");
                scanner.nextLine(); // Clear invalid input
            }
        } while (choice != 0);
    }
}


    // Load Data from file, Read each Line, Parse and create objects.
    private static void loadSuperbowlData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int year = Integer.parseInt(parts[0].substring(0, 4));
                String date = parts[0].substring(5);
                String superbowlNumber = parts[1];
                String winningTeam = parts[2];
                int winningPoints = Integer.parseInt(parts[3]);
                String losingTeam = parts[4];
                int losingPoints = Integer.parseInt(parts[5]);
                String mvp = parts[6];
                String stadium = parts[7];
                String city = parts[8];
                String state = parts[9];

                superbowls.add(new Superbowl(year, date, superbowlNumber, winningTeam, winningPoints,
                        losingTeam, losingPoints, mvp, stadium, city, state));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // List within specified range
    private static void listSuperbowl(Scanner scanner) {
        int startYear = 0;
        int endYear = 0;

        // Input validation for start year
        while (true) {
            System.out.print("Enter start year (1967-2024) > ");
            try {
                startYear = scanner.nextInt();
                if (startYear < 1967 || startYear > 2024) {
                    System.out.println("Start year must be between 1967 and 2024.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid year.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        // Input validation for end year
        while (true) {
            System.out.print("Enter end year (1967-2024) > ");
            try {
                endYear = scanner.nextInt();
                if (endYear < 1967 || endYear > 2024) {
                    System.out.println("End year must be between 1967 and 2024.");
                    continue;
                }
                if (endYear < startYear) {
                    System.out.println("End year must not be earlier than start year.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid year.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        // Dyanmic Collumn Width
        int yearWidth = "Year".length() + 2; // Padding
        int superbowlWidth = "Superbowl No.".length() + 2;
        int championWidth = "Champions".length() + 2;
        int runnerUpWidth = "Runners-up".length() + 2;

        // Adjusts widths for longest data
        for (Superbowl sb : superbowls) {
            if (sb.getYear() >= startYear && sb.getYear() <= endYear) {
                yearWidth = Math.max(yearWidth, String.valueOf(sb.getYear()).length() + 2);
                superbowlWidth = Math.max(superbowlWidth, sb.getSuperbowlNumber().length() + 2);
                championWidth = Math.max(championWidth, sb.getWinningTeam().length() + 2);
                runnerUpWidth = Math.max(runnerUpWidth, sb.getLosingTeam().length() + 2);
            }
        }

        // Print Table
        String format = "| %-"+yearWidth+"s | %-"+superbowlWidth+"s | %-"+championWidth+"s | %-"+runnerUpWidth+"s |";
        String separator = "-" + "-".repeat(yearWidth + 2) + "-" + "-".repeat(superbowlWidth + 2) + "-" +
                           "-".repeat(championWidth + 2) + "-" + "-".repeat(runnerUpWidth + 2) + "-";

        System.out.println("    "); // Whitespace before table
        System.out.println(separator); 
        System.out.printf(format + "\n", "Year", "Superbowl No.", "Champions", "Runners-up");
        System.out.println(separator);

        // Print table rows
        for (Superbowl sb : superbowls) {
            if (sb.getYear() >= startYear && sb.getYear() <= endYear) {
                System.out.printf(format + "\n", sb.getYear(), sb.getSuperbowlNumber(), sb.getWinningTeam(), sb.getLosingTeam());
            }
        }
        System.out.println(separator);
        System.out.println("    "); // Whitespace after table
    }


    // Select superbowl by year and display it's details
    private static void selectSuperbowl(Scanner scanner) {
        int year = 0;

        // Input validation for year
        while (true) {
            System.out.print("Enter championship year > ");
            try {
                year = scanner.nextInt();
                if (year < 1967 || year > 2024) {
                    System.out.println("Choose from 1967 - 2024.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid year.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        // Search for Superbowl by year
        for (Superbowl sb : superbowls) {
            if (sb.getYear() == year) {
                String[] lines = sb.toString().split("\n");
                int boxWidth = 80; // Set box width for formatting

                String border = "-" + "-".repeat(boxWidth) + "-";

                // Print the formatted box
                System.out.println(border);
                System.out.printf("| %-78s |\n", ""); // Blank line for padding
                for (String line : lines) {
                    System.out.printf("| %-78s |\n", centerAlign(line, boxWidth - 2));
                }
                System.out.printf("| %-78s |\n", ""); // Blank line for padding
                System.out.println(border);
                return;
            }
        }
        System.out.println("No Superbowl found for the given year.");
    }

    // center-align text
    private static String centerAlign(String text, int width) {
        int padding = (width - text.length()) / 2;
        String pad = " ".repeat(Math.max(0, padding));
        return pad + text + pad + (text.length() % 2 == 0 ? "" : " ");
    }




// Search for superbowl by team or state (Search is case-insensitive, shows all related results to search keyword)
private static void searchSuperbowl(Scanner scanner) {
    int searchChoice;
    do {
        System.out.println("----------------------------");
        System.out.println("Search superbowls by:      ");
        System.out.println("----------------------------");
        System.out.println("Team .......................1");
        System.out.println("State ......................2");
        System.out.println("Main menu ..................0");
        System.out.print("Enter choice:> ");

        // Handle invalid input
        try {
            searchChoice = scanner.nextInt();
            scanner.nextLine();

            switch (searchChoice) {
                case 1: {
                    System.out.print("Enter search term for NFL team (e.g., Giants) > ");
                    String team = scanner.nextLine();
                    searchByTeam(team);
                    break;
                }
                case 2: {
                    System.out.print("Enter search term for U.S. state (e.g., Florida) > ");
                    String state = scanner.nextLine();
                    searchByState(state);
                    break;
                }
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice, please enter a valid option.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number between 0 and 2.");
            scanner.nextLine(); // Clear invalid input
            searchChoice = -1; // Reset to an invalid value to remain in the menu
        }
    } while (searchChoice != 0);
}

private static void searchByTeam(String team) {
    List<String> processedTeams = new ArrayList<>(); // Track teams that have already been processed
    String searchTerm = team.trim().toLowerCase(); // Normalize the search term

    // Filter matching teams
    for (Superbowl sb : superbowls) {
        if (sb.getWinningTeam().toLowerCase().contains(searchTerm) || sb.getLosingTeam().toLowerCase().contains(searchTerm)) {
            String fullTeamName = sb.getWinningTeam().toLowerCase().contains(searchTerm) ? sb.getWinningTeam() : sb.getLosingTeam();
            if (!processedTeams.contains(fullTeamName)) {
                processedTeams.add(fullTeamName);
            }
        }
    }

    if (processedTeams.isEmpty()) {
        System.out.println("No results found for the given team.");
        return;
    }

    //  Dynamic Collumn width
    int teamWidth = "Team".length();
    int appearancesWidth = "No. appearances".length();
    int detailsWidth = "Details".length();

    // Find max width for each column
    for (String fullTeamName : processedTeams) {
        teamWidth = Math.max(teamWidth, fullTeamName.length());
        appearancesWidth = Math.max(appearancesWidth, String.valueOf(processedTeams.size()).length());
        for (Superbowl sb : superbowls) {
            String detail = sb.getYear() + " (" + sb.getSuperbowlNumber() + "), " +
                    (sb.getWinningTeam().equalsIgnoreCase(fullTeamName) ? "Winner" : "Runner-up");
            detailsWidth = Math.max(detailsWidth, detail.length());
        }
    }

    // Print table header
    String format = "| %-"+teamWidth+"s | %-"+appearancesWidth+"s | %-"+detailsWidth+"s |\n";
    String separator = "-" + "-".repeat(teamWidth + 2) + "-" + "-".repeat(appearancesWidth + 2) + "-" + "-".repeat(detailsWidth + 2);

    System.out.println(separator);
    System.out.printf(format, "Team", "No. appearances", "Details");
    System.out.println(separator);

    // Print table rows for each team
    for (String fullTeamName : processedTeams) {
        List<Superbowl> matchingSuperbowls = new ArrayList<>();
        for (Superbowl sb : superbowls) {
            if (sb.getWinningTeam().equalsIgnoreCase(fullTeamName) || sb.getLosingTeam().equalsIgnoreCase(fullTeamName)) {
                matchingSuperbowls.add(sb);
            }
        }

        // Sort by year
        matchingSuperbowls.sort(Comparator.comparingInt(Superbowl::getYear));

        // Print the team header row
        System.out.printf(format, fullTeamName, matchingSuperbowls.size(), matchingSuperbowls.get(0).getYear() + " (" + matchingSuperbowls.get(0).getSuperbowlNumber() + "), " +
                (matchingSuperbowls.get(0).getWinningTeam().equalsIgnoreCase(fullTeamName) ? "Winner" : "Runner-up"));

        // Print additional rows for the team
        for (int i = 1; i < matchingSuperbowls.size(); i++) {
            Superbowl match = matchingSuperbowls.get(i);
            String result = String.format("%d (%s), %s",
                    match.getYear(),
                    match.getSuperbowlNumber(),
                    match.getWinningTeam().equalsIgnoreCase(fullTeamName) ? "Winner" : "Runner-up");
            System.out.printf(format, "", "", result);
        }

        // Add the divider after each team
        System.out.println(separator);
    }
}


    // Search for superbowls by state
    private static void searchByState(String state) {
        List<Superbowl> filteredSuperbowls = new ArrayList<>();
        String searchTerm = state.trim().toLowerCase();

        // Filter by State
        for (Superbowl sb : superbowls) {
            if (sb.getState().toLowerCase().contains(searchTerm)) {
                filteredSuperbowls.add(sb);
            }
        }

        if (filteredSuperbowls.isEmpty()) {
            System.out.println("No results found for the given state.");
            return;
        }

        // Dynamic Width
        int stateWidth = "State".length();
        int superbowlWidth = "Superbowl".length();
        int cityStadiumWidth = "City & Stadium".length();

        for (Superbowl sb : filteredSuperbowls) {
            stateWidth = Math.max(stateWidth, sb.getState().length() + 5);
            superbowlWidth = Math.max(superbowlWidth, (sb.getSuperbowlNumber() + " (" + sb.getYear() + ")       ").length());
            cityStadiumWidth = Math.max(cityStadiumWidth, (sb.getCity() + ", " + sb.getStadium() + "       ").length());
        }

        filteredSuperbowls.sort(Comparator.comparing(Superbowl::getState).thenComparingInt(Superbowl::getYear));

        String format = "| %-"+stateWidth+"s | %-"+superbowlWidth+"s | %-"+cityStadiumWidth+"s |\n";
        String separator = "-" + "-".repeat(stateWidth + 2) + "-" + "-".repeat(superbowlWidth + 2) + "-" + "-".repeat(cityStadiumWidth + 2);

        // Print Table
        System.out.println(separator);
        System.out.printf(format, "State", "Superbowl", "City & Stadium");
        System.out.println(separator);

        // Track current processed state
        String currentState = "";
        for (Superbowl sb : filteredSuperbowls) {
            if (!sb.getState().equals(currentState) && !currentState.isEmpty()) {
                System.out.println(separator);
            }

            String stateName = sb.getState().equals(currentState) ? "" : sb.getState() + "     ";
            currentState = sb.getState();
            System.out.printf(format, stateName, sb.getSuperbowlNumber() + " (" + sb.getYear() + ")     ", sb.getCity() + ", " + sb.getStadium() + "     ");
        }

        System.out.println(separator);
    }
}

// Class Declaration
class Superbowl {
    private final int year;
    private final String date;
    private final String superbowlNumber;
    private final String winningTeam;
    private final int winningPoints;
    private final String losingTeam;
    private final int losingPoints;
    private final String mvp;
    private final String stadium;
    private final String city;
    private final String state;

    public Superbowl(int year, String date, String superbowlNumber, String winningTeam, int winningPoints,
                     String losingTeam, int losingPoints, String mvp, String stadium, String city, String state) {
        this.year = year;
        this.date = date;
        this.superbowlNumber = superbowlNumber;
        this.winningTeam = winningTeam;
        this.winningPoints = winningPoints;
        this.losingTeam = losingTeam;
        this.losingPoints = losingPoints;
        this.mvp = mvp;
        this.stadium = stadium;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("Superbowl %s\nVenue: %s in %s, %s\nThe %s beat the %s by %d points to %d\nThe most valuable player award went to %s\n",
                superbowlNumber, stadium, city, state, winningTeam, losingTeam, winningPoints, losingPoints, mvp);
    }

    public int getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public String getSuperbowlNumber() {
        return superbowlNumber;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public int getWinningPoints() {
        return winningPoints;
    }

    public String getLosingTeam() {
        return losingTeam;
    }

    public int getLosingPoints() {
        return losingPoints;
    }

    public String getMvp() {
        return mvp;
    }

    public String getStadium() {
        return stadium;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}