package gym.management;

import gym.customers.Client;
import gym.customers.FactoryPerson;
import gym.customers.Observer;
import gym.customers.Person;
import gym.management.Sessions.Session;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

/**
 * Singleton class representing the Gym management system.
 * This class manages clients, instructors, sessions, and various gym-related operations.
 */
public class Gym {
    private static Gym instance;
    private Secretary secretary;
    private String gymName;
    private final ArrayList<Client> allC;
    private final ArrayList<Instructor> allInst;
    private final ArrayList<String> actionsHistory;
    private final ArrayList<Session> allSes;
    private final HashMap<Instructor, Integer> sesInst;
    private int gymBalance;
    private final HashMap<String, ArrayList<Client>> allObForDate;

    /**
     * Private constructor to initialize gym data.
     */
    private Gym() {
        allC = new ArrayList<>();
        allInst = new ArrayList<>();
        allSes = new ArrayList<>();
        actionsHistory = new ArrayList<>();
        sesInst = new HashMap<>();
        allObForDate = new HashMap<>();
        gymBalance = 0;
    }

    /**
     * Provides the singleton instance of the Gym class.
     * @return The singleton Gym instance.
     */
    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    public Secretary getSecretary() {
        return this.secretary;
    }

    /**
     * Sets a new Secretary for the gym.
     * @param p The Person object to be assigned as Secretary.
     * @param s The salary of the Secretary.
     */
    public void setSecretary(Person p, int s) {
        this.secretary = FactoryPerson.newSecretary(p, s);
        addActionsHistory("A new secretary has started working at the gym: " + secretary.getName());
    }

    public void setName(String name) {
        gymName = name;
    }

    public String getGymName() {
        return gymName;
    }

    /**
     * Adds a new client to the list of all gym members.
     * @param c The Client to add.
     */
    public void addAllC(Client c) {
        allC.add(c);
    }

    /**
     * Retrieves the list of all clients in the gym.
     * @return An ArrayList of all clients.
     */
    public ArrayList<Client> getAllC() {
        return allC;
    }

    /**
     * Retrieves the list of all instructors in the gym.
     * @return An ArrayList of all instructors.
     */
    public ArrayList<Instructor> getAllInst() {
        return allInst;
    }

    /**
     * Adds a new instructor to the list of all instructors.
     * @param inst The Instructor to add.
     */
    public void addAllInst(Instructor inst) {
        allInst.add(inst);
    }

    /**
     * Adds an entry to the actions history log.
     * @param s The action description to add.
     */
    public void addActionsHistory(String s) {
        actionsHistory.add(s);
    }

    /**
     * Retrieves the gym's action history.
     * @return An ArrayList of action descriptions.
     */
    public ArrayList<String> getActionsHistory() {
        return actionsHistory;
    }

    /**
     * Adds a new session to the list of all sessions.
     * @param s The Session to add.
     */
    public void addAllSes(Session s) {
        allSes.add(s);
    }

    /**
     * Retrieves the list of all sessions in the gym.
     * @return An ArrayList of all sessions.
     */
    public ArrayList<Session> getAllSes() {
        return allSes;
    }

    /**
     * Retrieves the mapping of instructors to their session counts.
     * @return A HashMap of instructors and their associated session counts.
     */
    public HashMap<Instructor, Integer> getHashInst() {
        return sesInst;
    }

    /**
     * Adds a session to an instructor's session count.
     * @param i The Instructor for whom to add a session.
     */
    public void addSesToInst(Instructor i) {
        if (containsInst(i)) {
            Integer currentVal = sesInst.get(i);
            if (currentVal == null) {
                currentVal = 0;
            }
            sesInst.put(i, currentVal + 1);
        } else {
            sesInst.put(i, 0);
        }
    }

    /**
     * Checks if an instructor exists in the list of all instructors.
     * @param i The Instructor to check.
     * @return True if the instructor exists, false otherwise.
     */
    public boolean containsInst(Instructor i) {
        for (Instructor instructor : allInst) {
            if (instructor.equalsPerson(i)) {
                return true;
            }
        }
        return false;
    }

    public void setGymBalance(int gymBalance) {
        this.gymBalance = gymBalance;
    }

    public int getGymBalance() {
        return gymBalance;
    }

    /**
     * Retrieves the mapping of dates to the clients who have a lesson in that date.
     * @return A HashMap of dates and their associated clients.
     */
    public HashMap<String, ArrayList<Client>> getAllObForDate() {
        return allObForDate;
    }

    /**
     * Adds a client to the list of observers for a specific date.
     * @param date The date for which to add the observer.
     * @param c    The Client to add.
     */
    public void addAllObForDate(String date, Client c) {
        boolean flag = false;
        if (allObForDate.get(date) == null) {
            ArrayList<Client> ob = new ArrayList<>();
            ob.add(c);
            allObForDate.put(date, ob);
        }
        for (int i = 0; i < allObForDate.get(date).size(); i++) {
            if (allObForDate.get(date).get(i).equalsPerson(c)) {
                flag = true;
            }
        }
        if (!flag) {
            allObForDate.get(date).add(c);
        }
    }

    /**
     * Changes the format of a date string from "dd-MM-yyyy" to "yyyy-MM-dd".
     * @param date The date string to reformat.
     * @return The reformatted date string.
     */
    public String changeDateFormat(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        return localDate.format(outputFormatter);
    }

    /**
     * Returns a string representation of the gym's details.
     * @return A formatted string representing the gym's details.
     */
    public String toString() {
        StringBuilder allInf = new StringBuilder();
        allInf.append("Gym Name: ").append(this.gymName).append("\n");
        allInf.append("Gym Secretary: ").append(this.secretary.toString()).append("\n");
        allInf.append("Gym Balance: ").append(this.gymBalance).append("\n\n");

        allInf.append("Clients Data:\n");
        for (Client client : this.allC) {
            allInf.append(client.toString()).append("\n");
        }

        allInf.append("\nEmployees Data:\n");
        for (Person employee : this.allInst) {
            allInf.append(employee.toString()).append("\n");
        }
        allInf.append(this.secretary.toString()).append("\n");

        allInf.append("\nSessions Data:\n");
        for (int i = 0; i < this.allSes.size(); i++) {
            if (i == this.allSes.size() - 1) {
                allInf.append(allSes.get(i).toString());
            } else {
                allInf.append(allSes.get(i).toString()).append("\n");
            }
        }
        return allInf.toString();
    }
}

