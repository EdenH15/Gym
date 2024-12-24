package gym.management.Sessions;

import gym.customers.Client;
import gym.management.Instructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a session in the gym system.
 */
public class Session {

    private final SessionType sessionType; // The type of the session.
    private final String date; // The date and time of the session "dd-MM-yyyy HH:mm".
    private final ForumType forumType; //  The forum type of the session.
    private final Instructor inst; // The instructor conducting the session.
    private final ArrayList<Client> registeredClients; // The list of clients registered for the session.

    /**
     * Constructs a new Session instance.
     * @param s     The type of the session.
     * @param date  The date and time of the session in the format "dd-MM-yyyy HH:mm".
     * @param f     The forum type of the session.
     * @param inst  The instructor for the session.
     */
    public Session(SessionType s, String date, ForumType f, Instructor inst) {
        sessionType = s;
        this.date = date;
        forumType = f;
        this.inst = inst;
        this.registeredClients = new ArrayList<>();
    }

    /**
     * Adds a client to the session.
     * @param c The client to add.
     */
    public void addClient(Client c) {
        this.registeredClients.add(c);
    }

    /**
     * Retrieves the list of clients registered for the session.
     * @return An ArrayList of registered clients.
     */
    public ArrayList<Client> getRegisteredClients() {
        return this.registeredClients;
    }

    /**
     * Checks if a client is registered for the session.
     * @param c The client to check.
     * @return true if the client is registered,  false otherwise.
     */
    public boolean isClientRegistered(Client c) {
        for (Client client : this.registeredClients) {
            if (client.equalsPerson(c)) {
                return true;
            }
        }
        return false;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public ForumType getForumType() {
        return forumType;
    }

    /**
     * @return The date and time of the session in the format "yyyy-MM-dd'T'HH:mm".
     */
    public String getDate() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(this.date, inputFormatter);
        return dateTime.format(outputFormatter);
    }

    /**
     * @return The date and time of the session in the format "dd-MM-yyyy HH:mm".
     */
    public String getDateFormat() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(this.getDate(), inputFormatter);
        return dateTime.format(outputFormatter);
    }

    /**
     * Retrieves only the time part of the session's date.
     * @return The time of the session in the format "HH:mm".
     */
    public String getOnlyTime() {
        String[] arr = this.date.split(" ");
        return arr[1];
    }

    /**
     * Retrieves only the date part of the session's date.
     * @return The date of the session in the format "dd-MM-yyyy".
     */
    public String getOnlyDate() {
        String[] arr = this.date.split(" ");
        return arr[0];
    }

    public Instructor getInst() {
        return inst;
    }

    /**
     * Checks if the specified date has passed compared to the current date and time.
     * @param date The date to check "dd-MM-yyyy HH:mm".
     * @return true if the date has passed,  false otherwise.
     */
    public static boolean dateHasPassed(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            LocalDateTime parsedDate1 = LocalDateTime.parse(date, formatter);
            LocalDateTime currentDate = LocalDateTime.now();
            return parsedDate1.isBefore(currentDate);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns a string representation of the session's details.
     * @return A formatted string containing all session's details.
     */
    @Override
    public String toString() {
        return "Session Type: " + this.sessionType.getName() +
                " | Date: " + this.getOnlyDate() + " " + this.getOnlyTime() +
                " | Forum: " + this.getForumType() +
                " | Instructor: " + this.getInst().getName() +
                " | Participants: " + registeredClients.size() + "/" + this.getSessionType().getMaxPerson();
    }
}

