import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import transport.Transport;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.text.StyleConstants;

public class RailwayReservationSystem {

    public static void main(String[] args) throws IOException, DocumentException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "User One", 25));
        users.add(new User("user2", "password2", "User Two", 30));

        Map<String, Transport> transports = new HashMap<>();
        transports.put("Surat to Ahmedabad", new Train("Rajdhani Express", "Surat to Ahmedabad", 200, 1200.0));
        transports.put("Ahmedabad to Surat", new Train("Shatabdi Express", "Ahmedabad to Surat", 150, 800.0));
        transports.put("Vadodara to Ahmedabad", new Train("Duronto Express", "Vadodara to Ahmedabad", 180, 1000.0));
        transports.put("Ahmedabad to Pune", new Train("Ahimsa Express", "Ahmedabad to Pune", 100, 1500.0));
        transports.put("Hyderabad to New Delhi", new Train("Andhra Pradesh Express", "Hyderabad to New Delhi", 200, 1200.0));
        transports.put("Delhi to Chennai", new Train("Shatabdi Express", "Delhi to Chennai", 150, 800.0));
        transports.put("Ahmadabad to Delhi", new Train("Ashram Express", "Ahmadabad to Delhi", 180, 1000.0));
        transports.put("Chennai to Delhi", new Train("Gatimaan Express", "Chennai to Delhi", 100, 1500.0));
        transports.put("Delhi to Mumbai", new Train("Rajdhani Express", "Delhi to Mumbai", 200, 1200.0));
        transports.put("Mumbai Central to Indore", new Train("Avantika Express", "Mumbai Central to Indore", 150, 800.0));
        transports.put("Mumbai to Chennai", new Train("Duronto Express", "Mumbai to Chennai", 180, 1000.0));
        transports.put("Ahmadabad to Patna", new Train("Azimabad Express", "Ahmadabad to Patna", 100, 1500.0));
        transports.put("Puri to Patna", new Train("Baidyanathdham Express", "Puri to Patna", 200, 1200.0));
        transports.put("Chennai to Bangalore", new Train("Brindavan Express", "Chennai to Bangalore", 150, 800.0));
        transports.put("Chennai to Hyderabad", new Train("Charminar Express", "Chennai to Hyderabad", 180, 1000.0));


        User currentUser = null;
        ArrayList<Booking> bookings = new ArrayList<>();

        while (true) {
            System.out.println("Railway Reservation System");
            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
            } else {
                System.out.println("3. View Trains");
                System.out.println("4. Book Tickets");
                System.out.println("5. Logout");
            }
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (currentUser == null) {
                if (choice == 1) {
                    System.out.print("Enter a username: ");
                    String username = scanner.nextLine();
                    System.out.println("Password must meet the following criteria:");
                    System.out.println("1. Minimum length of 8 characters.");
                    System.out.println("2. At least one digit.");
                    System.out.println("3. At least one uppercase letter.");
                    System.out.println("4. At least one lowercase letter.");
                    System.out.print("Enter a password: ");
                    String password = scanner.nextLine();
                    do {
                        System.out.print("Enter a password: ");
                        password = scanner.nextLine();
                    } while (!passwordIsValid(password));
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your age: ");
                    int age = scanner.nextInt();
                    users.add(new User(username, password, name, age));
                    scanner.nextLine();  // Consume newline
                    System.out.println("Registration successful!");
                } else if (choice == 2) {
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    for (User user : users) {
                        if (user.getUsername().equals(username) && user.authenticate(password)) {
                            currentUser = user;
                            System.out.println("Login successful!");
                            break;
                        }
                    }
                    if (currentUser == null) {
                        System.out.println("Invalid username or password.");
                    }
                }
            } else {
                if (choice == 3) {
                    System.out.print("Enter Departure Point: ");
                    String departure = scanner.nextLine();
                    System.out.print("Enter Destination Point: ");
                    String destination = scanner.nextLine();

                    // Display available trains based on departure and destination
                    System.out.println("Available Trains from " + departure + " to " + destination + ":");
                    System.out.println("+-----------------------------------------+");
                    System.out.println("| Route                 | Train Name       | Seats | Price |");
                    System.out.println("+-----------------------+------------------+-------+-------+");

                    for (Transport transport : transports.values()) {
                        if (transport.getDeparture().equals(departure) && transport.getDestination().equals(destination)) {
                            String formattedRoute = String.format("| %-22s | %-16s | %5d | %5.2f |", transport.getRoute(), transport.getName(), transport.getAvailableSeats(), transport.getTicketPrice());
                            System.out.println(formattedRoute);
                        }
                    }
                    System.out.println("+-----------------------+------------------+-------+-------+");
                } else if (choice == 4) {
                    // Collect user preferences
                    System.out.print("Enter Preferences (e.g., Window Seat, Aisle Seat, etc.): ");
                    String preferences = scanner.nextLine();

                    System.out.print("Enter Route: ");
                    String route = scanner.nextLine();
                    System.out.print("Enter Departure Point: ");
                    String departure = scanner.nextLine();
                    System.out.print("Enter Destination Point: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter Journey Date: ");
                    String journeyDate = scanner.nextLine();
                    System.out.print("Enter Number of Tickets: ");
                    int numTickets = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    int startingSeatNumber = allocateSeatNumber(numTickets);
                    int endingSeatNumber = startingSeatNumber + numTickets - 1;

                    Booking booking = new Booking(route, departure, destination, numTickets, journeyDate);
                    bookings.add(booking);

                    Transport transport = transports.get(route);
                    if (transport != null) {
                        transport.bookTickets(numTickets);
                        Document document = new Document(PageSize.A4);

                        try {
                            // Provide the path to the PDF file you want to create
                            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("example.pdf"));
                            document.open();
                            PdfContentByte canvas = writer.getDirectContentUnder();
                            Image backgroundImage = Image.getInstance("Images//rail.jpg"); // Provide the path to your background image
                            backgroundImage.scaleAbsolute(200, 300);
                            backgroundImage.setAbsolutePosition(380, 400);
                            canvas.addImage(backgroundImage);
                            float borderWidth = 5; // Adjust the border width as needed
                            canvas.rectangle(borderWidth, borderWidth, PageSize.A4.getWidth() - borderWidth, PageSize.A4.getHeight() - borderWidth);
                            canvas.stroke();
                            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
                            Paragraph paragraph = new Paragraph("Train Ticket\n", font);
                            paragraph.setAlignment(Element.ALIGN_CENTER);
                            Paragraph para = new Paragraph("__________________\n\n", font);
                            para.setAlignment(Element.ALIGN_CENTER);
                            document.add(paragraph);
                            document.add(para);
                            font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

                            // Add content to the PDF
                            Paragraph details = new Paragraph(
                                    "Passenger Name: " + currentUser.getName() + "\n" +
                                            "Passenger Age: " + currentUser.getAge() + "\n" +
                                            "Passenger Preferences: " + preferences + "\n" +
                                            "Route: " + route + "\n" +
                                            "Departure Point: " + departure + "\n" +
                                            "Destination Point: " + destination + "\n" +
                                            "Journey Date: " + journeyDate + "\n" +
                                            "Number of Tickets: " + numTickets + "\n" +
                                            "Train Name: " + transport.getName() + "\n" +
                                            "Seat Numbers: " + startingSeatNumber + "-D5 to " + endingSeatNumber + "-D5 " + "\n" +
                                            "Total Cost: Rs. " + (numTickets * transport.getTicketPrice()) + "\n", font
                            );
                            details.setAlignment(Element.ALIGN_LEFT);
                            document.add(details);

                            // Close the document
                            document.close();

                            System.out.println("PDF created successfully.");
                        } catch (DocumentException | FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Invalid route. Booking failed.");
                    }
                } else if (choice == 5) {
                    currentUser = null;
                    System.out.println("Logout successful!");
                } else if (choice == 6) {
                    System.out.println("Exiting the program.");
                    break;
                }
            }
        }
    }
    private static boolean passwordIsValid(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }

        if (!password.matches(".\\d.")) {
            System.out.println("Password must contain at least one digit.");
            return false;
        }

        if (!password.matches(".[A-Z].")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        }

        if (!password.matches(".[a-z].")) {
            System.out.println("Password must contain at least one lowercase letter.");
            return false;
        }
        return true;
    }

    private static int allocateSeatNumber(int numTickets) {
        int nextSeatNumber = 67;
        int startingSeatNumber = nextSeatNumber;
        nextSeatNumber += numTickets;
        return startingSeatNumber;
    }
}


// Train class inherits from Transport
class Train extends Transport {
    public Train(String name, String route, int availableSeats, double ticketPrice) {
        super(name, route, availableSeats, ticketPrice);
    }

    @Override
    public String getDeparture() {
        // Implement this method to return the departure point for a train
        return getRoute().split(" to ")[0];
    }

    @Override
    public String getDestination() {
        // Implement this method to return the destination point for a train
        return getRoute().split(" to ")[1];
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public boolean authenticate(String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }

    @Override
    public int getAge() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAge'");
    }

    @Override
    public String getPreferences() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPreferences'");
    }

    @Override
    public int getNumTickets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNumTickets'");
    }

    @Override
    public String getJourneyDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJourneyDate'");
    }
}

class User {
    private String username;
    private String password;
    private String name;
    private int age;

    public User(String username, String password, String name, int age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Booking {
    private String route;
    private String departure;
    private String destination;
    private int numTickets;
    private String journeyDate;

    public Booking(String route, String departure, String destination, int numTickets, String journeyDate) {
        this.route = route;
        this.departure = departure;
        this.destination = destination;
        this.numTickets = numTickets;
        this.journeyDate = journeyDate;
    }

    public String getRoute() {
        return route;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public String getJourneyDate() {
        return journeyDate;
    }
}
