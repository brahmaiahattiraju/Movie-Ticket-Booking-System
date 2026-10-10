import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

class User {
    private String username;
    private String password;

    public boolean register(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return false;
        }

        this.username = username;
        this.password = password;
        return true;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

// Encapsulation
class Movie {
    private String name;
    private String category;
    private double ticketPrice;
    private TreeSet<Integer> availableSeatNumbers = new TreeSet<>();

    public Movie(String name, String category, double ticketPrice, int availableSeats) {
        this.name = name;
        this.category = category;
        this.ticketPrice = ticketPrice;
        for (int seatNumber = 1; seatNumber <= availableSeats; seatNumber++) {
            availableSeatNumbers.add(seatNumber);
        }
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getAvailableSeats() {
        return availableSeatNumbers.size();
    }

    public List<Integer> bookSeats(int tickets) {
        List<Integer> bookedSeatNumbers = new ArrayList<>();
        if (tickets <= 0 || tickets > availableSeatNumbers.size()) {
            return bookedSeatNumbers;
        }

        for (int i = 0; i < tickets; i++) {
            int seatNumber = availableSeatNumbers.first();
            availableSeatNumbers.remove(seatNumber);
            bookedSeatNumbers.add(seatNumber);
        }
        return bookedSeatNumbers;
    }

    public void releaseSeats(List<Integer> seatNumbers) {
        availableSeatNumbers.addAll(seatNumbers);
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Abstraction
abstract class Payment {
    public abstract boolean pay(double amount);
}

// Inheritance
class UpiPayment extends Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing UPI payment...");
        System.out.printf("Payment successful: Rs. %.2f%n", amount);
        return true;
    }
}

// Inheritance
class CardPayment extends Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing card payment...");
        System.out.printf("Payment successful: Rs. %.2f%n", amount);
        return true;
    }
}

// Booking class
class Booking {
    private static int nextId = 1001;

    private int bookingId;
    private String customerName;
    private Movie movie;
    private int tickets;
    private List<Integer> seatNumbers;
    private double ticketAmount;
    private double foodAmount;
    private boolean cancelled;
    private List<String> foodOrders = new ArrayList<>();

    public Booking(String customerName, Movie movie, int tickets,
            List<Integer> seatNumbers) {
        this.bookingId = nextId++;
        this.customerName = customerName;
        this.movie = movie;
        this.tickets = tickets;
        this.seatNumbers = new ArrayList<>(seatNumbers);
        this.ticketAmount = movie.getTicketPrice() * tickets;
    }

    public void addFoodOrder(FoodItem item, int quantity) {
        double itemTotal = item.getPrice() * quantity;
        foodOrders.add(item.getName() + " x " + quantity
            + " - Rs. " + String.format("%.2f", itemTotal));
        foodAmount += itemTotal;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean cancel() {
        if (cancelled) {
            return false;
        }
        movie.releaseSeats(seatNumbers);
        cancelled = true;
        return true;
    }

    public void printTicket() {
        System.out.println("\n===== BOOKING DETAILS =====");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customerName);
        System.out.println("Movie: " + movie.getName());
        System.out.println("Category: " + movie.getCategory());
        System.out.println("Tickets: " + tickets);
        System.out.println("Seat numbers: " + seatNumbers);
        System.out.printf("Ticket Amount: Rs. %.2f%n", ticketAmount);
        System.out.println("Food Items:");
        if (foodOrders.isEmpty()) {
            System.out.println("  None");
        } else {
            for (String foodOrder : foodOrders) {
                System.out.println("  " + foodOrder);
            }
        }
        System.out.printf("Food Amount: Rs. %.2f%n", foodAmount);
        System.out.printf("Grand Total: Rs. %.2f%n", ticketAmount + foodAmount);
        System.out.println("Status: " + (cancelled ? "CANCELLED" : "CONFIRMED"));
        System.out.println("=============================");
    }
}

// Main class
public class MovieTicketBooking {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== USER REGISTRATION =====");
        User user = new User();
        while (true) {
            System.out.print("Create a username: ");
            String username = sc.nextLine();
            System.out.print("Create a password: ");
            String password = sc.nextLine();

            if (user.register(username, password)) {
                break;
            }
            System.out.println("Username and password cannot be empty.");
        }

        System.out.println("\n===== USER LOGIN =====");
        boolean loggedIn = false;
        String loggedInUsername = null;
        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            if (user.login(username, password)) {
                loggedIn = true;
                loggedInUsername = username;
                System.out.println("Login successful. Welcome, " + username + "!");
                break;
            }
            System.out.println("Incorrect username or password.");
        }

        if (!loggedIn) {
            System.out.println("Too many failed login attempts. Goodbye.");
            sc.close();
            return;
        }

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Pushpa 2", "Action", 180, 50));
        movies.add(new Movie("Devara", "Action", 150, 40));
        movies.add(new Movie("Kalki 2898 AD", "Thriller", 200, 30));
        movies.add(new Movie("F2", "Comedy", 130, 35));
        movies.add(new Movie("HIT: The Third Case", "Thriller", 170, 25));
        movies.add(new Movie("Sita Ramam", "Romance", 160, 40));
        movies.add(new Movie("Salaar", "Action", 190, 45));
        movies.add(new Movie("Hi Nanna", "Romance", 150, 30));

        FoodItem[] foodItems = {
            new FoodItem("Popcorn", 120),
            new FoodItem("Nachos", 150),
            new FoodItem("Cold Drink", 80)
        };
        List<Booking> bookings = new ArrayList<>();

        System.out.println("===== MOVIE TICKET BOOKING =====");
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Display movies");
            System.out.println("2. Search movie");
            System.out.println("3. Book tickets");
            System.out.println("4. Booking history");
            System.out.println("5. Cancel booking");
            System.out.println("0. Exit");

            int choice = readInt(sc, "Choose an option: ");
            if (choice == 0) {
                System.out.println("Thank you!");
                break;
            } else if (choice == 1) {
                displayMovies(movies);
            } else if (choice == 2) {
                searchMovies(sc, movies);
            } else if (choice == 3) {
                bookTickets(sc, movies, bookings, foodItems, loggedInUsername);
            } else if (choice == 4) {
                showBookingHistory(bookings, loggedInUsername);
            } else if (choice == 5) {
                cancelBooking(sc, bookings, loggedInUsername);
            } else {
                System.out.println("Invalid menu option.");
            }
        }

        sc.close();
    }

    private static void displayMovies(List<Movie> movies) {
        System.out.println("\nAvailable Movies:");
        for (int i = 0; i < movies.size(); i++) {
            printMovie(i + 1, movies.get(i));
        }
    }

    private static void printMovie(int number, Movie movie) {
        String prefix = number > 0 ? number + ". " : "";
        System.out.printf("%s%s | Category: %s | Ticket: Rs. %.2f | Seats available: %d%n",
            prefix, movie.getName(), movie.getCategory(),
            movie.getTicketPrice(), movie.getAvailableSeats());
    }

    private static void searchMovies(Scanner sc, List<Movie> movies) {
        System.out.print("Enter a movie name (or part of the name): ");
        String query = sc.nextLine().trim().toLowerCase(Locale.ROOT);
        if (query.isEmpty()) {
            System.out.println("Movie name cannot be empty.");
            return;
        }

        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getName().toLowerCase(Locale.ROOT).contains(query)) {
                printMovie(0, movie);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No movie found matching \"" + query + "\".");
        }
    }

    private static void bookTickets(Scanner sc, List<Movie> movies,
            List<Booking> bookings, FoodItem[] foodItems, String customerName) {
        displayMovies(movies);
        int choice = readInt(sc, "Choose a movie number (0 to return): ");
        if (choice == 0) {
            return;
        }
        if (choice < 1 || choice > movies.size()) {
            System.out.println("Invalid movie choice.");
            return;
        }

        Movie selectedMovie = movies.get(choice - 1);
        int tickets = readInt(sc, "Enter number of tickets: ");
        if (tickets <= 0 || tickets > selectedMovie.getAvailableSeats()) {
            System.out.println("Invalid ticket count or not enough seats.");
            return;
        }

        double total = selectedMovie.getTicketPrice() * tickets;
        System.out.printf("Ticket price: Rs. %.2f x %d = Rs. %.2f%n",
            selectedMovie.getTicketPrice(), tickets, total);
        Payment payment = selectPayment(sc);
        if (payment == null) {
            System.out.println("Invalid payment method. Booking was not created.");
            return;
        }

        if (!payment.pay(total)) {
            System.out.println("Payment failed. Booking was not created.");
            return;
        }
        List<Integer> seatNumbers = selectedMovie.bookSeats(tickets);
        if (seatNumbers.isEmpty()) {
            System.out.println("Seats are no longer available.");
            return;
        }

        Booking booking = new Booking(customerName, selectedMovie, tickets, seatNumbers);
        bookings.add(booking);
        System.out.println("\nTicket booked successfully!");
        addFoodOrder(sc, foodItems, booking);
        booking.printTicket();
    }

    private static void addFoodOrder(Scanner sc, FoodItem[] foodItems, Booking booking) {
        System.out.println("\n===== FOOD MENU =====");
        for (int i = 0; i < foodItems.length; i++) {
            System.out.printf("%d. %s - Rs. %.2f%n",
                i + 1, foodItems[i].getName(), foodItems[i].getPrice());
        }
        System.out.println("Enter 0 quantity for any item you do not want.");

        int[] quantities = new int[foodItems.length];
        double foodTotal = 0;
        for (int i = 0; i < foodItems.length; i++) {
            quantities[i] = readInt(sc, "Quantity of " + foodItems[i].getName() + ": ");
            if (quantities[i] < 0) {
                System.out.println("Quantity cannot be negative; using 0.");
                quantities[i] = 0;
            }
            foodTotal += foodItems[i].getPrice() * quantities[i];
        }

        if (foodTotal == 0) {
            return;
        }

        System.out.printf("Food order total: Rs. %.2f%n", foodTotal);
        System.out.println("Pay for food items:");
        Payment foodPayment = selectPayment(sc);
        if (foodPayment != null && foodPayment.pay(foodTotal)) {
            for (int i = 0; i < foodItems.length; i++) {
                if (quantities[i] > 0) {
                    booking.addFoodOrder(foodItems[i], quantities[i]);
                }
            }
        } else {
            System.out.println("Food payment was not completed; food was not added.");
        }
    }

    private static void showBookingHistory(List<Booking> bookings, String customerName) {
        System.out.println("\n===== BOOKING HISTORY =====");
        boolean found = false;
        for (Booking booking : bookings) {
            if (booking.getCustomerName().equals(customerName)) {
                booking.printTicket();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No bookings found.");
        }
    }

    private static void cancelBooking(Scanner sc, List<Booking> bookings,
            String customerName) {
        int bookingId = readInt(sc, "Enter booking ID to cancel (0 to return): ");
        if (bookingId == 0) {
            return;
        }

        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId
                    && booking.getCustomerName().equals(customerName)) {
                if (booking.cancel()) {
                    System.out.println("Booking " + bookingId
                        + " cancelled. The seats are available again.");
                } else {
                    System.out.println("This booking has already been cancelled.");
                }
                return;
            }
        }
        System.out.println("Booking not found for this customer.");
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static Payment selectPayment(Scanner sc) {
        System.out.println("1. UPI Payment");
        System.out.println("2. Card Payment");
        int paymentChoice = readInt(sc, "Choose payment method: ");

        if (paymentChoice == 1) {
            return new UpiPayment();
        }
        if (paymentChoice == 2) {
            return new CardPayment();
        }
        return null;
    }
}