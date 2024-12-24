package gym.customers;

import java.util.ArrayList;

/**
 * Represents a gym client who extends from Person class and implements the Observer interface.
 */
public class Client extends Person implements Observer {

    /**
     * A list to store notifications for the client.
     */
    private final ArrayList<String> notifications;

    /**
     * Constructs a new Client based on an existing Person instance.
     * @param p The Person instance containing the base information for the client.
     */
    public Client(Person p) {
        super(p);
        notifications = new ArrayList<>();
    }

    /**
     * Adds a new notification for the client.
     * @param mes The message of the notification to add.
     */
    public void newNotify(String mes) {
        notifications.add(mes);
    }

    /**
     * Retrieves all notifications for the client as a formatted string.
     * @return A string representation of all notifications.
     */
    public String getNotifications() {
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < notifications.size(); i++) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(notifications.get(i));
        }
        s.append("]");
        return s.toString();
    }

    /**
     * Retrieves the name of the client.
     * @return The name of the client.
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Returns a string representation of the client's details.
     * @return A string containing the client's details.
     */
    @Override
    public String toString() {
        return "ID: " + this.getID() +
                " | Name: " + this.getName() +
                " | Gender: " + this.getGender() +
                " | Birthday: " + this.getBirth() +
                " | Age: " + this.getAge() +
                " | Balance: " + this.getMoney();
    }
}

