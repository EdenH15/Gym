package gym.customers;

import gym.management.Instructor;
import gym.management.Secretary;
import gym.management.Sessions.SessionType;

import java.util.ArrayList;

public class FactoryPerson {

    public static Secretary newSecretary(Person p,int s){
        return new Secretary(p,s);
    }
    public static Client newClient(Person p){

        return new Client(p);
    }
    public static Instructor newInstructor(Person p,int salary,ArrayList<SessionType>sesType){
     return new Instructor(p,salary,sesType);
    }

}
