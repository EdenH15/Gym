package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.customers.FactoryPerson;
import gym.customers.Observer;
import gym.customers.Person;
import gym.management.Sessions.ForumType;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;
import java.util.ArrayList;

/**
 * The Secretary class represents a gym secretary and provides methods for managing clients, instructors,
 * sessions, payments, and sending notifications.
 */
public class Secretary extends Person {
    private final int salary;
    private final Gym g;

    /**
     * Constructor to initialize the Secretary with a Person and a salary.
     * @param p The Person object representing the Secretary.
     * @param s The monthly salary of the Secretary.
     */
    public Secretary(Person p, int s) {
        super(p);
        this.salary = s;
        g = Gym.getInstance();
    }

    /**
     * Ensures the current instance is the gym's secretary.
     * @throws NullPointerException if the current person is not the gym secretary.
     */
    public void isCurrentSecretary()throws NullPointerException{
        if (!g.getSecretary().equalsPerson(this)){
            throw new NullPointerException();
        }
    }

    /**
     * Registers a new client if not already registered and meets the minimum age requirement.
     * @param p2 The Person object representing the client.
     * @return The newly registered Client.
     * @throws DuplicateClientException if the client is already registered.
     * @throws InvalidAgeException if the client is under 18 years old.
     */
    public Client registerClient(Person p2) throws DuplicateClientException, InvalidAgeException {
        isCurrentSecretary();
        if (formerClient(p2)) {
            throw new DuplicateClientException("Error: The client is already registered");
        } else if (p2.getAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        } else {
            Client newClient = FactoryPerson.newClient(p2);
            g.addAllC(newClient);
            g.addActionsHistory("Registered new client: " + p2.getName());
            return newClient;
        }
    }

    /**
     * Unregisters an existing client from the gym.
     * @param c2 The Client to be unregistered.
     * @throws ClientNotRegisteredException if the client is not registered.
     */
    public void unregisterClient(Client c2) throws ClientNotRegisteredException {
        isCurrentSecretary();
        if (!formerClient(c2)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        } else {
            for (int i = 0; i < g.getAllC().size(); i++) {
                if (c2.equalsPerson(g.getAllC().get(i))) {
                    g.getAllC().remove(g.getAllC().get(i));
                }
            }
            g.addActionsHistory("Unregistered client: " + c2.getName());
        }
    }

    /**
     * Hires a new instructor and assigns session types they are qualified to teach.
     * @param p The Person object representing the Instructor.
     * @param salary The salary of the Instructor.
     * @param sesType The session types the instructor is qualified to teach.
     * @return The newly hired Instructor.
     */
    public Instructor hireInstructor(Person p, int salary, ArrayList<SessionType> sesType) {
        isCurrentSecretary();
        Instructor newInst = FactoryPerson.newInstructor(p, salary, sesType);
        g.addSesToInst(newInst);
        g.addAllInst(newInst);
        g.addActionsHistory("Hired new instructor: " + p.getName() + " with salary per hour: " + salary);
        return newInst;
    }

    /**
     * Adds a new session with a qualified instructor.
     * @param sesType The type of the session.
     * @param date The date of the session.
     * @param fT The forum type (e.g., male, female, seniors).
     * @param i The instructor conducting the session.
     * @return The created Session.
     * @throws InstructorNotQualifiedException if the instructor is not qualified for the session.
     */
    public Session addSession(SessionType sesType, String date, ForumType fT, Instructor i) throws InstructorNotQualifiedException {
        isCurrentSecretary();
        for (int j = 0; j < i.getSessionType().size(); j++) {
            if (sesType.equals(i.getSessionType().get(j))) {
                Session ses = new Session(sesType, date, fT, i);
                g.addAllSes(ses);
                g.addSesToInst(i);
                g.addActionsHistory("Created new session: "+sesType.getName() +" on "+ ses.getDate()+  " with instructor: "+ i.getName());
                return ses;

            }
        }
        throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
    }

    /**
     * Registers a client to a session after checking several conditions like age, balance, session availability, etc.
     * @param c1 The Client to register.
     * @param s1 The Session the client is enrolling in.
     * @throws ClientNotRegisteredException if the client is not registered with the gym.
     * @throws DuplicateClientException if the client is already registered for the session.
     */
    public void registerClientToLesson(Client c1, Session s1) throws ClientNotRegisteredException, DuplicateClientException {
        isCurrentSecretary();
        boolean flag=false;
        if (!formerClient(c1)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        } else if (s1.isClientRegistered(c1)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        } else {
            if (s1.getForumType().equals(ForumType.Seniors)&& c1.getAge()<65){
                flag=true;
                g.addActionsHistory("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
            }
            if (Session.dateHasPassed(s1.getDateFormat())){
                flag=true;
                g.addActionsHistory("Failed registration: Session is not in the future");
            }
            if (s1.getForumType().equals(ForumType.Male)&&c1.getGender().equals(Gender.Female)||s1.getForumType().equals(ForumType.Female)&&c1.getGender().equals(Gender.Male)){
                flag=true;
                g.addActionsHistory("Failed registration: Client's gender doesn't match the session's gender requirements");
            }

            if (c1.getMoney()<s1.getSessionType().getPrice()){
                flag=true;
                g.addActionsHistory("Failed registration: Client doesn't have enough balance");
            }
            if (s1.getRegisteredClients().size()==s1.getSessionType().getMaxPerson()){
                g.addActionsHistory("Failed registration: No available spots for session");
                flag=true;
            }
            if (!flag) {
                s1.addClient(c1);
                int sesPrice = s1.getSessionType().getPrice();
                c1.setMoney(c1.getMoney() - sesPrice);
                g.setGymBalance(g.getGymBalance() + sesPrice);
                g.addAllObForDate(s1.getOnlyDate(), c1);
                g.addActionsHistory("Registered client: " + c1.getName() + " to session: " + s1.getSessionType().getName() + " on " + s1.getDate() + " for price: " + s1.getSessionType().getPrice());
            }
        }
    }
    /**
     * Pays the salaries of all instructors and the secretary.
     */
    public void paySalaries() {
        isCurrentSecretary();
        for (int i = 0; i < g.getAllInst().size(); i++) {
            Instructor inst = g.getAllInst().get(i);
            int amount = g.getHashInst().get(inst);
            int salary = amount * inst.getSalary();
            inst.setMoney(inst.getMoney()+salary);// pays the instructor its salary
            int gymB = g.getGymBalance();
            g.setGymBalance(gymB - salary);
        }
        int gymB = g.getGymBalance();
        this.setMoney(this.getMoney()+this.salary);
        g.setGymBalance(gymB - this.salary);
        g.addActionsHistory("Salaries have been paid to all employees");

    }

    /**
     * Prints all the actions recorded in the gym's action history.
     */
    public void printActions() {
        isCurrentSecretary();
        for (String s : g.getActionsHistory()) {
            System.out.println(s);
        }
    }

    /**
     * Sends a notification to all clients registered for a specific session.
     * @param ses The session for which clients will be notified.
     * @param s The message to be sent.
     */
    public void notify(Session ses, String s) {
        isCurrentSecretary();
        for (Client client : ses.getRegisteredClients()) {
            client.newNotify(s);
        }
        g.addActionsHistory("A message was sent to everyone registered for session "+ ses.getSessionType().getName()+ " on "+ ses.getDate()+ " : "+s);
    }

    /**
     * Sends a notification to all clients registered for sessions on a specific date.
     * @param date The date for which clients will be notified.
     * @param s The message to be sent.
     */
    public void notify(String date, String s) {
        isCurrentSecretary();
        for (Client client : g.getAllObForDate().get(date)) {
            client.newNotify(s);
        }
        g.addActionsHistory("A message was sent to everyone registered for a session on "+g.changeDateFormat(date)+" : "+s);
    }

    /**
     * Sends a notification to all clients of the gym.
     * @param s The message to be sent.
     */
    public void notify(String s) {
        isCurrentSecretary();
        for (Observer observer : g.getAllC()) {
            observer.newNotify(s);
        }
        g.addActionsHistory("A message was sent to all gym clients: "+s);
    }

    /**
     * Provides a string representation of the Secretary, including personal details and salary.
     * @return A string representation of the Secretary.
     */
    public String toString(){

        return super.toString()+" | Role: Secretary | Salary per Month: "+this.salary;
                //"ID: "+ this.getID()+ " | Name: "+this.getName()+ " | Gender: "+this.getGender()+ " | Birthday: " +this.getBirth()+ " | Age: "+this.getAge()+ " | Balance: "+this.getMoney()+ " | Role: Secretary | Salary per Month: "+this.salary;
    }
}




