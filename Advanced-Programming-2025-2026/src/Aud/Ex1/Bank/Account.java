package Aud.Ex1.Bank;

public abstract class Account {
    private String name;
    private String accountNumber;
    private double balance;

    public Account(String name, String accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount){
        this.balance  = this.balance + amount;
    }

    public void withdraw(double amount) {
        if(this.balance < amount){
            return;
        }else{
            this.balance -= amount;
        }
    }
}
