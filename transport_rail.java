package transport;

public interface Transport_rail {
    public String getName();
    public String getRoute();
    public int getAvailableSeats();
    public double getTicketPrice();
    public void bookTickets(int num);
    public abstract String getDeparture();
    public abstract String getDestination();
    public String getUsername();
    public boolean authenticate(String password);
    public int getAge();
    public String getPreferences();
    public int getNumTickets();
    public String getJourneyDate();
}
