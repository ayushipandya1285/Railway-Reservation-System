package transport;

public abstract class Transport implements Transport_rail {
    private String name;
    private String route;
    private int availableSeats;
    private double ticketPrice;

    public Transport(String name, String route, int availableSeats, double ticketPrice) {
        this.name = name;
        this.route = route;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void bookTickets(int num) {
        if (num <= availableSeats) {
            availableSeats -= num;
            double totalCost = num * ticketPrice;
            System.out.println(num + " tickets booked for " + name);
            System.out.println("Total Cost: Rs. " + totalCost);
        } else {
            System.out.println("Sorry, there are not enough seats available for " + name);
        }
    }
}


