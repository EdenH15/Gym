package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

/**
 * Represents an Instructor in the gym system.
 */
public class Instructor extends Person {

    private final int salary; // The salary of the instructor.
    private final ArrayList<SessionType> sessionType; // A list of session types the instructor is certified to teach.

    /**
     * Constructs a new Instructor object.
     * @param p        The Person instance containing the base information for the instructor.
     * @param salary   The salary per hour of the instructor.
     * @param sesType  The list of session types the instructor is certified to teach.
     */
    public Instructor(Person p, int salary, ArrayList<SessionType> sesType) {
        super(p);
        this.salary = salary;
        sessionType = new ArrayList<>(sesType);
    }

    public int getSalary() {
        return salary;
    }

    public ArrayList<SessionType> getSessionType() {
        return sessionType;
    }

    /**
     * Returns a string representation of the instructor's details.
     * @return A formatted string containing the instructor's details.
     */
    public String toString() {
        return "ID: " + this.getID() +
                " | Name: " + this.getName() +
                " | Gender: " + this.getGender() +
                " | Birthday: " + this.getBirth() +
                " | Age: " + this.getAge() +
                " | Balance: " + this.getMoney() +
                " | Role: Instructor | Salary per Hour: " + this.salary +
                " | Certified Classes: " + arrS(sessionType);
    }

    /**
     * Converts a list of session types into a comma-separated string.
     * @param arr The list of session types.
     * @return A formatted string of session types.
     */
    public String arrS(ArrayList<SessionType> arr) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < arr.size() - 1; i++) {
            str.append(arr.get(i).toString()).append(", ");
        }
        str.append(arr.getLast().toString());
        return str.toString();
    }
}

