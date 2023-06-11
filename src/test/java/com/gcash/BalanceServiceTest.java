package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceTest {

    //successful tests

    @Test
    void successfulGetBalance() {
        //Setup
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String id = repo.createAccount("Loreine", 123.4); //creating a test account
        var balance = new BalanceService(repo);

        //Kick and verify
        Assertions.assertEquals(123.4, balance.getBalance(id));
    }

    @Test
    void successfulDebit() {
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String id = repo.createAccount("Loreine", 123.4); //creating a test account
        var balance = new BalanceService(repo);
        Double amount = 123.4;

        //Kick and verify
        Assertions.assertEquals(0, balance.debit(id, amount));

    }

    @Test
    void successfulCredit() {
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String id = repo.createAccount("Loreine", 123.4); //creating a test account
        var balance = new BalanceService(repo);
        Double amount = 23.4;

        //Kick and verify
        Assertions.assertEquals(146.8, balance.credit(id, amount));
        //for credit counted ba if 0 yung iccredit

    }

    @Test
    void successfulTransfer() {
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String from = repo.createAccount("Loreine", 123.4);
        String to = repo.createAccount("Adriana", 100.0);
        var balance = new BalanceService(repo);
        Double amount = 123.4;

        //Kick and verify
        Assertions.assertEquals(223.4, balance.transfer(from, to, amount));
        //should a transfer of 0 be counted
    }

    // unsuccessful tests

    @Test
    void noAccountToGet() {
        //Same setup as successfulGetBalance
        //Since credit and debit use getBalance, and transfer uses credit and debit, covered na
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String faker = "Adriana";
        var balance = new BalanceService(repo);

        //Kick and verify
        Assertions.assertNull(balance.getBalance(faker));

    }
    @Test
    void amountIsBiggerThanBalanceDebit() {
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String id = repo.createAccount("Loreine", 123.4); //creating a test account
        var balance = new BalanceService(repo);
        Double amount = 567.8;

        //Kick and verify
        Assertions.assertNull(balance.debit(id, amount));
    }

    @Test
    void amountIsZeroOrNegativeCredit() {
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String id = repo.createAccount("Loreine", 123.4); //creating a test account
        var balance = new BalanceService(repo);
        Double amount = 0.0;

        //Kick and verify
        Assertions.assertNull(balance.credit(id, amount));
    }

    @Test
    void transferAmountIsMoreThanBalance() {
        var repo = new AccountRepository(); //creating an instance of AccountRepository to pasok into BalanceService
        String from = repo.createAccount("Loreine", 123.4);
        String to = repo.createAccount("Adriana", 100.0);
        var balance = new BalanceService(repo);
        Double amount = 1000.0;

        //Kick and verify
        Assertions.assertNull(balance.transfer(from, to, amount));

    }

    @Test
    void transferringToSameAccount() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = 1.0;

        //Kick and verify
        Assertions.assertNull(balance.transfer(id, id, amount));
    }

}