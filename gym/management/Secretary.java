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
import java.util.HashMap;

public class Secretary extends Person {
    private final int salary;
    private final Gym g;


    public Secretary(Person p, int s) {
        super(p);
        this.salary = s;
        g = Gym.getInstance();
    }

    public void isCurrentSecretary()throws NullPointerException{
        if (!g.getSecretary().equalsPerson(this)){
            throw new NullPointerException();
        }
    }

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

    public Instructor hireInstructor(Person p, int salary, ArrayList<SessionType> sesType) {
        isCurrentSecretary();
        Instructor newInst = FactoryPerson.newInstructor(p, salary, sesType);
        g.addSesToInst(newInst);
        g.addAllInst(newInst);
        g.addActionsHistory("Hired new instructor:" + p.getName() + " with salary per hour: " + salary);
        return newInst;
    }

    public Session addSession(SessionType sesType, String date, ForumType fT, Instructor i) throws InstructorNotQualifiedException {
        isCurrentSecretary();
        for (int j = 0; j < i.getSessionType().size(); j++) {
            if (sesType.equals(i.getSessionType().get(j))) {
                Session ses = new Session(sesType, date, fT, i);
                g.addAllSes(ses);
                g.addSesToInst(i);
                return ses;
            }
        }
        throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
    }

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
            if (s1.getForumType().equals(ForumType.Male)&&c1.getGender().equals(Gender.Female)||s1.getForumType().equals(ForumType.Female)&&c1.getGender().equals(Gender.Male)){
                flag=true;
                g.addActionsHistory("Failed registration: Client's gender doesn't match the session's gender requirements");
            }
            if (Session.dateHasPassed(s1.getDate())){
                flag=true;
                g.addActionsHistory("Failed registration: Session is not in the future");
            }
            if (c1.getMoney()<s1.getSessionType().getPrice()){
                flag=true;
                g.addActionsHistory("Failed registration: Client doesn't have enough balance");
            }
            if (!flag) {
                s1.addClient(c1);
                int sesPrice = s1.getSessionType().getPrice();
                c1.setMoney(c1.getMoney() - sesPrice);
                g.setGymBalance(g.getGymBalance() + sesPrice);
                g.addAllObForDate(s1.getOnlyDate(), c1);
                System.out.println("Registered client: " + c1.getName() + " to session: " + s1.getSessionType().getName() + " on " + s1.getDate() + " for price: " + s1.getSessionType().getPrice());
            }
            }
        }
    public void paySalaries() {
        isCurrentSecretary();
        HashMap<Instructor, Integer> h = g.getHashInst();
        int gymB = g.getGymBalance();
        for (int i = 0; i < g.getAllInst().size(); i++) {
            Instructor inst = g.getAllInst().get(i);
            int m = h.get(inst);
            int salary = m * inst.getSalary();
            inst.setMoney(inst.getMoney() + salary); // pays the instructor it's salary
            h.put(inst, 0);
            g.setGymBalance(gymB - salary);
        }
        this.setMoney(this.salary);
        g.setGymBalance(gymB - this.salary);

    }

    public void printActions() {
        isCurrentSecretary();
        for (String s : g.getActionsHistory()) {
            System.out.println(s);
        }
    }

    public void notify(Session ses, String s) {
        isCurrentSecretary();
        for (Client client : ses.getRegisteredClients()) {
            client.newNotify(s);
        }
    }

    public void notify(String date, String s) {
        isCurrentSecretary();
        for (Client client : g.getAllObForDate().get(date)) {
            client.newNotify(s);
        }
    }

    public void notify(String s) {
        isCurrentSecretary();
        for (Observer observer : g.getAllC()) {
            observer.newNotify(s);
        }
    }
}



