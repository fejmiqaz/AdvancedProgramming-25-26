package Aud.Ex1.Bank;

import java.util.List;

public class Bank {
    private List<Account> accounts;

    public Bank(List<Account> accounts) {
        this.accounts = accounts;
    }

    public double totalAssets(){
        double total = 0.0;
        for(Account a : accounts) {
            total += a.getBalance();
        }
        return total;
    }

    public void addInterest(){
        for(Account a : accounts){
            if(a instanceof InterestBearingAccount){
                InterestBearingAccount acc = (InterestBearingAccount) a;
                acc.addInterest();
            }
        }
    }
}
