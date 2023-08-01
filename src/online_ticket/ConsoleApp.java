package online_ticket;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private static final TicketDAO ticketDAO = new TicketDAO();
    private static final DAO userDAO = new UserDAO();
    private static User currentUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.print("Enter your username: ");
        String username = scanner.next();
        currentUser = ticketDAO.getUserByUsername(username);

        if (currentUser == null) {
            System.out.println("User not found. Do you want to create a new user? (Y/N)");
            String createNewUser = scanner.next();

            if ("Y".equalsIgnoreCase(createNewUser)) {
                createUser(scanner, username);
            } else {
                System.out.println("Exiting the application.");
                return;
            }
        }

        System.out.println("Welcome, " + currentUser.getUsername() + "!");

        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    raiseTicket(scanner);
                    break;
                case 2:
                    resolveTicket(scanner);
                    break;
                case 3:
                    viewAllTickets();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Application closed.");
    }

    private static void displayMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Raise a ticket");
        System.out.println("2. Resolve a ticket");
        System.out.println("3. View all tickets");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void raiseTicket(Scanner scanner) {
        if (currentUser.isAgent()) {
            System.out.println("Agents cannot raise tickets. Only users can raise tickets.");
            return;
        }

        System.out.print("Enter the issue description: ");
        String issueDescription = scanner.nextLine();
        ticketDAO.create(currentUser.getId(), issueDescription);
    }

    private static void resolveTicket(Scanner scanner) {
        if (!currentUser.isAgent()) {
            System.out.println("Only agents can resolve tickets.");
            return;
        }

        System.out.print("Enter the ticket ID to resolve: ");
        int ticketId = scanner.nextInt();
        ticketDAO.resolveTicket(ticketId);
    }

    private static void viewAllTickets() {
        List<Ticket> tickets = ticketDAO.getAllTickets();

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        System.out.println("\n===== All Tickets =====");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void createUser(Scanner scanner, String username) {
        System.out.print("Enter the role (USER/AGENT): ");
        String role = scanner.next().toUpperCase();

        System.out.print("Enter the password: ");
        String password = scanner.next();

        userDAO.create(username, role, password);
        currentUser = ticketDAO.getUserByUsername(username);
        System.out.println("User created successfully!");
    }
}
