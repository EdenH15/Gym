package gym.management.Sessions;

import gym.customers.Client;
import gym.management.Instructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        // הגדרת פורמט התאריך החדש
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // המרת התאריך לפורמט DateTime
        LocalDateTime dateTime = LocalDateTime.parse(this.date, inputFormatter);

        // המרת התאריך לפורמט הרצוי והחזרתו
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            Date parsedDate1 = formatter.parse(date);
            Date currentDate = new Date();
            return parsedDate1.before(currentDate);
        } catch (Exception e) {
            //System.err.println("Error parsing date: " + e.getMessage());
            return false;
        }
    }
}
