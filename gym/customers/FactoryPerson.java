package gym.customers;

import gym.management.Instructor;
import gym.management.Secretary;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

/**
 * A factory class for creating instances of different types of persons in the gym system.
 */
public class FactoryPerson {

    /**
     * Creates a new Secretary instance.
     * @param p The Person containing the base information for the secretary.
     * @param s The salary of the secretary.
     * @return A new Secretary instance.
     */
    public static Secretary newSecretary(Person p, int s) {
        return new Secretary(p, s);
    }

    /**
     * Creates a new Client instance.
     * @param p The Person containing the base information for the client.
     * @return A new Client instance.
     */
    public static Client newClient(Person p) {
        return new Client(p);
    }

    /**
     * Creates a new Instructor instance.
     * @param p The Person containing the base information for the instructor.
     * @param salary The salary of the instructor.
     * @param sesType A list of session types the instructor is qualified to teach.
     * @return A new Instructor instance.
     */
    public static Instructor newInstructor(Person p, int salary, ArrayList<SessionType> sesType) {
        return new Instructor(p, salary, sesType);
    }

}
