package gym.management.Sessions;

import gym.customers.Client;
import gym.management.Instructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Session {
    private final SessionType sessionType;
    private final String date;
    private final ForumType forumType;
    private final Instructor inst;
    private final ArrayList<Client> registeredClients;



    public Session(SessionType s, String date, ForumType f, Instructor inst){
        sessionType=s;
        this.date=date;
        forumType=f;
        this.inst=inst;
        this.registeredClients = new ArrayList<>();
    }

    public void addClient(Client c){
        this.registeredClients.add(c);
    }
    public ArrayList<Client> getRegisteredClients(){
        return this.registeredClients;
    }
    public boolean isClientRegistered(Client c){

        for(Client client:this.registeredClients){
            if (client.equalsPerson(c)){
                return true;
            }
        }
        return false;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public ForumType getForumType() {
        return forumType;
    }

    public String getDate() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(this.date, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    public String getDateFormat() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime dateTime = LocalDateTime.parse(this.getDate(), inputFormatter);
        return dateTime.format(outputFormatter);
    }
    public String getOnlyTime(){
        String[] arr = this.date.split(" ");
        return arr[1];
    }
    public String getOnlyDate(){
        String[] arr = this.date.split(" ");
        return arr[0];
    }

    public Instructor getInst() {
        return inst;
    }
    public static boolean dateHasPassed(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        try {
            LocalDateTime parsedDate1 = LocalDateTime.parse(date,formatter);
            LocalDateTime currentDate = LocalDateTime.now();
            return parsedDate1.isBefore(currentDate);
        } catch (Exception e) {
            //System.err.println("Error parsing date: " + e.getMessage());
            return false;
        }
    }

    public String toString(){
        return "Session Type: " +this.sessionType.getName() +" | Date: "+this.getOnlyDate()+" "+this.getOnlyTime()+ " | Forum: "+this.getForumType()+ " | Instructor: "+this.getInst().getName()+ " | Participants: " +registeredClients.size()+"/"+this.getSessionType().getMaxPerson();
    }
}
