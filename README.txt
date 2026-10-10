MOVIE TICKET BOOKING - CORE JAVA OOP MINI PROJECT

Requirements:
- JDK installed (javac and java commands available)

Run on Windows:
1. Open Command Prompt.
2. Navigate to the folder containing MovieTicketBooking.java.
3. Compile:
   javac MovieTicketBooking.java
4. Run:
   java MovieTicketBooking

Features:
- Registers a username and password at startup, then requires a successful login.
- Displays a catalog of movies with categories, ticket prices, and available seats.
- Searches movies by full or partial name and displays matching movie details.
- Books one or more tickets.
- Automatically assigns the lowest available seat numbers and prints them on the ticket.
- Calculates the ticket total from ticket price multiplied by ticket count.
- Generates a unique booking ID for each booking.
- Displays the current customer's booking history.
- Cancels a booking and makes its seats available again.
- Simulates UPI or card payment (no real payment is made).
- Confirms the booking only if the simulated payment succeeds.
- Updates available seats after booking and returns the exact seat numbers after cancellation.
- Offers popcorn, nachos, and cold drinks after a ticket is booked.
- Simulates a separate payment for food and includes paid food items in the booking summary.

OOP concepts:
- Encapsulation: private fields in Movie and Booking.
- Abstraction: abstract Payment class.
- Inheritance: UpiPayment and CardPayment extend Payment.
- Polymorphism: both payment classes override processPayment().

After login, use the numbered menu to display or search the catalog, book tickets,
review booking history, cancel a booking by its ID, or exit. Each booking and its
seat availability are held in memory for the current program run. Payments are a
console simulation; cancellation does not process a payment refund.

Accounts exist only for the current program run; registration data is not saved.
This is an educational console demo. It does not connect to a real payment gateway or database.
