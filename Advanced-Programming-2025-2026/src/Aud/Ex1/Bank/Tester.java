package Aud.Ex1.Bank;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        NonInterestCheckingAccount a = new NonInterestCheckingAccount("first", "111", 100.0);
        InterestCheckingAccount b = new InterestCheckingAccount("ard", "222", 100.0);
        PlatinumCheckingAccount c = new PlatinumCheckingAccount("hmd", "333", 100.0);
        List<Account> accounts = new ArrayList<>();
        accounts.add(a);
        accounts.add(b);
        accounts.add(c);
        Bank bank = new Bank(accounts);

        System.out.println(bank.totalAssets());
        bank.addInterest();
        System.out.println(bank.totalAssets());

    }
}
