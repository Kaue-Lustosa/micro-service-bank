package ufrn.imd.compositeservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name="tb_account")
public class Account implements Serializable{

    @Id
    private String id;

    private double balance;

    public Account(){}

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