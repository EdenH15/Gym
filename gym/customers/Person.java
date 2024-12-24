package gym.customers;

import gym.Money;
import gym.management.Gender;
import gym.management.Gym;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a person.
 */
public class Person {

    private final String name; // The name of the person.
    private final String birth; //  The date of birth of the person "dd-MM-yyyy".
    private final Money money; // The balance of the person.
    private final Gender gender; //  The gender of the person.
    private static int idCounter = 1111; // Used to generate unique IDs for persons.
    private final int id; // The unique ID of the person.

    /**
     * Constructs a new Person instance.
     * @param name  The name of the person.
     * @param money The initial financial balance of the person.
     * @param g     The gender of the person.
     * @param birth The date of birth of the person "dd-MM-yyyy".
     */
    public Person(String name, int money, Gender g, String birth) {
        this.name = name;
        this.money = new Money(money);
        this.birth = birth;
        this.gender = g;
        this.id = idCounter;
        idCounter++;
    }

    /**
     * Constructs a new Person instance based on an existing Person object.
     * @param p The Person object to copy.
     */
    public Person(Person p) {
        this.name = p.name;
        this.gender = p.gender;
        this.birth = p.birth;
        this.money = p.money;
        this.id = p.id;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public int getMoney() {
        return this.money.getPersonMoney();
    }

    public void setMoney(int m) {
        this.money.setPersonMoney(m);
    }

    public Gender getGender() {
        return gender;
    }

    public int getID() {
        return this.id;
    }

    public int getIDCounter() {
        return idCounter;
    }

    /**
     * Compares this person with another person to check if they are the same.
     * @param p1 The person to compare with.
     * @return true if both persons have the same ID, false otherwise.
     */
    public boolean equalsPerson(Person p1) {
        return this.id == p1.getID();
    }

    /**
     * Checks if the person is a former client of the gym.
     * @param p The person to check.
     * @return true if the person is a former client, false otherwise.
     */
    public static boolean formerClient(Person p) {
        Gym g = Gym.getInstance();
        for (int i = 0; i < g.getAllC().size(); i++) {
            if (p.equalsPerson(g.getAllC().get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the age of the person based on their date of birth.
     * @return The age of the person in years.
     */
    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(birth, formatter);
        LocalDate dateNow = LocalDate.now();
        Period age = Period.between(birthDate, dateNow);
        return age.getYears();
    }
}


