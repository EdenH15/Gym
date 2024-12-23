package gym.customers;


import java.util.ArrayList;

public class Client extends Person implements Observer {
    private final ArrayList<String> notifications;

    public Client(Person p){
        super(p);
        notifications=new ArrayList<>();
    }
    public void newNotify(String mes) {
        notifications.add(mes);
    }
    public String getNotifications(){
        StringBuilder s= new StringBuilder("[");
        for (int i = 0; i <notifications.size() ; i++) {
            if(i>0){
                s.append(", ");
            }
            s.append(notifications.get(i));
        }
        s.append("]");
        return s.toString();
    }

    public String getName() {
        return super.getName();
    }

    public String toString(){
    return "ID: "+ this.getID()+ " | Name: "+this.getName()+ " | Gender: "+this.getGender()+ " | Birthday: " +this.getBirth()+ " | Age: "+this.getAge()+ " | Balance: "+this.getMoney();
}


}
