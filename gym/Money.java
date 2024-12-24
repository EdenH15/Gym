package gym;

public class Money {

    /**
     * A helper class to handle all balances for every person in the gym system.
     */
    private int personMoney;


    public Money(int money){
        this.personMoney=money;
    }

    public int getPersonMoney(){
        return this.personMoney;
    }
    public void setPersonMoney(int amount){
        this.personMoney=amount;

    }
}
