package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

public class Instructor extends Person {
    private final int salary;

    private final ArrayList<SessionType> sessionType;

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
    public String toString(){
        return "ID: "+ this.getID()+ " | Name: "+this.getName()+ " | Gender: "+this.getGender()+ " | Birthday: " +this.getBirth()+ " | Age: "+this.getAge()+ " | Balance: "+this.getMoney()+ " | Role: Instructor | Salary per Hour: "+this.salary+" | Certified Classes: "+arrS(sessionType);

    }
    public String arrS(ArrayList<SessionType> arr){
        StringBuilder str=new StringBuilder();
        for (int i = 0; i <arr.size()-1 ; i++) {
            str.append(arr.get(i).toString()).append(", ");
        }
        str.append(arr.getLast().toString());
        return str.toString();
    }


}
