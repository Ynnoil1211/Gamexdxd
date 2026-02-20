package org.example;

public class Wallet {
    private int balance;
    public Wallet(){
        this.balance = 1000;
    }
    public int getBalance() {
        return balance;
    }
    public void addBalance(int balance) {
        if(balance>0) this.balance += balance;
        else System.out.println("Cannot be negative.");
    }
    public boolean deductBalance(int amount){
        if(amount>0 && balance>=amount){
            balance-=amount;
            System.out.println("Successful.");
            return true;
        }
        else {
            System.out.println("Not enough balance. ");
            return false;
        }
    }
}
