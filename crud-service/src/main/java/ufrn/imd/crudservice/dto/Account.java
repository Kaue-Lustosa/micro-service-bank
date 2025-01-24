package ufrn.imd.crudservice.dto;

public class Account {
    private String id;
    private double balance;

    public Account() {}

    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(String id) {
            this.id = id;
    }

    public void setBalance(double balance) {
            this.balance = balance;
    }

    public void depositOp(double amount){
        this.balance += amount;
    }

    public void withdrawOp(double amount) {

        this.balance -= amount;


    }

    @Override
    public String toString() {
        return "Account: " + id + ", balance: " + balance;
    }
}