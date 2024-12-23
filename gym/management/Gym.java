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

public class Gym {
    private static Gym instance;
    private Secretary secretary;
    private String gymName;
    private final ArrayList<Client> allC;
    private final ArrayList<Instructor> allInst;
    private final ArrayList<String> actionsHistory;
    private final ArrayList<Session> allSes;
    private final HashMap<Instructor,Integer> sesInst;
    private int gymBalance;
    private final HashMap<String,ArrayList<Client>> allObForDate;


    private Gym(){
        allC=new ArrayList<>();
        allInst=new ArrayList<>();
        allSes=new ArrayList<>();
        actionsHistory=new ArrayList<>();
        sesInst=new HashMap<>();
        allObForDate=new HashMap<>();
        gymBalance=0;

    }

    public static Gym getInstance() {
        if(instance==null){
            instance=new Gym();
        }
        return instance;
    }

    public Secretary getSecretary() {
        return this.secretary;
    }

    public void setSecretary(Person p,int s) {
//        if (this.secretary != null) {
//           this.secretary=null;
//        }
       this.secretary = FactoryPerson.newSecretary(p,s);
        addActionsHistory("A new secretary has started working at the gym:"+secretary.getName());
    }

    public void setName(String name) {
        gymName=name;

    }

    public String getGymName(){
        return gymName;
    }

    public void addAllC(Client c){
        allC.add(c);
    }
    public ArrayList<Client> getAllC(){
        return allC;
    }
    public ArrayList<Instructor> getAllInst(){
        return allInst;
    }
    public void addAllInst(Instructor inst){
        allInst.add(inst);
    }

    public void addActionsHistory(String s){
        actionsHistory.add(s);
    }
    public ArrayList<String> getActionsHistory(){
        return actionsHistory;
    }
    public void addAllSes(Session s){
        allSes.add(s);
    }
    public ArrayList<Session> getAllSes() {
        return allSes;
    }
    public HashMap<Instructor,Integer> getHashInst(){
        return sesInst;
    }
    public void addSesToInst(Instructor i){
        if (containsInst(i)){
            Integer currentVal=sesInst.get(i);
            if (currentVal == null) {
                currentVal = 0;
            }
            sesInst.put(i,currentVal+1);
        }
        else sesInst.put(i,0);
    }
    public boolean containsInst(Instructor i){
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

    public HashMap<String, ArrayList<Client>> getAllObForDate() {
        return allObForDate;
    }

    public void addAllObForDate(String date,Client c){
        boolean flag=false;
        if (allObForDate.get(date)==null){
            ArrayList<Client> ob=new ArrayList<>();
            ob.add(c);
            allObForDate.put(date,ob);
        }
        for (int i = 0; i <allObForDate.get(date).size() ; i++) {
            if (allObForDate.get(date).get(i).equalsPerson(c)) {
                flag=true;
            }
        }
        if(!flag) {
            allObForDate.get(date).add(c);
        }
    }
    public String changeDateFormat(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        return localDate.format(outputFormatter);
    }

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
