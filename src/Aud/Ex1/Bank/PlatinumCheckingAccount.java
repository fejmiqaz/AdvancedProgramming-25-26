package Aud.Ex1.Bank;

public class PlatinumCheckingAccount extends InterestCheckingAccount{
    public PlatinumCheckingAccount(String name, String accountNumber, double balance) {
        super(name, accountNumber, balance);
    }

    @Override
    public void addInterest() {
        setBalance(getBalance() + (1 + INTEREST_RATE * 2));
    }
}
