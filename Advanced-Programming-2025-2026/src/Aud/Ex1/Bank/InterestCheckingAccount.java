package Aud.Ex1.Bank;

import java.util.List;

public class InterestCheckingAccount extends Account implements InterestBearingAccount {

    protected static double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String name, String accountNumber, double balance) {
        super(name, accountNumber, balance);
    }

    @Override
    public void addInterest() {
        setBalance(getBalance() * (1 + INTEREST_RATE));
    }
}
