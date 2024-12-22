package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

public class Instructor extends Person {
    private final int salary;

    private final ArrayList<SessionType> sessionType;
    private int id;

    public Instructor(Person p, int salary, ArrayList<SessionType> sesType) {
        super(p);
        this.salary=salary;
        sessionType=new ArrayList<>(sesType);
    }

    public int getSalary(){
        return salary;
    }

    public ArrayList<SessionType> getSessionType(){
        return sessionType;
    }
}
