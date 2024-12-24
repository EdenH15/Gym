package gym.customers;

/**
 * An interface representing an observer, using the observer design pattern.
 * Observers receive notifications.
 */
public interface Observer {

    /**
     * Receives a new notification message.
     * @param mes The message of the notification.
     */
    void newNotify(String mes);
}
