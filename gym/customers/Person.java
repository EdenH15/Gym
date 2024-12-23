package gym.customers;

import gym.Money;
import gym.management.Gender;
import gym.management.Gym;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Person implements Observer {
    private final String name;
    private final String birth;
    private final Money money;
    private final Gender gender;
    private static int idCounter = 1111;
    private final int id;



    public Person(String name, int money, Gender g, String birth) {
        this.name = name;
        this.money = new Money(money);
        this.birth = birth;
        this.gender = g;
        this.id=idCounter;
        idCounter++;
    }

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
    public  void setMoney(int m){
        this.money.setPersonMoney(m);
    }

    public Gender getGender() {
        return gender;
    }

    public int getID() {
        return this.id;
    }

    public int getIDCounter(){
        return idCounter;

    }

    public boolean equalsPerson(Person p1) {
        return this.id==p1.getID();
    }
    public static boolean formerClient(Person p) {
        Gym g = Gym.getInstance();
        for (int i = 0; i < g.getAllC().size(); i++) {
            if (p.equalsPerson(g.getAllC().get(i))) {
                return true;
            }
        }
        return false;
    }


    public int getAge(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(birth,formatter);
        LocalDate dateNow = LocalDate.now();
        Period age = Period.between(birthDate, dateNow);
        return age.getYears();
    }

    @Override
    public void newNotify(String mes) {
    //
    }
}

