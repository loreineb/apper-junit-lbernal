package com.gcash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceTest {

    //Successful tests

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
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = 123.4;

        Assertions.assertEquals(0, balance.debit(id, amount));
    }

    @Test
    void successfulCredit() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = 23.4;

        Assertions.assertEquals(146.8, balance.credit(id, amount));
    }

    @Test
    void successfulTransfer() {
        var repo = new AccountRepository();
        String from = repo.createAccount("Loreine", 123.4);
        String to = repo.createAccount("Adriana", 100.0);
        var balance = new BalanceService(repo);
        Double amount = 123.4;

        Assertions.assertEquals(223.4, balance.transfer(from, to, amount));
    }

    // I. Unsuccessful tests: account does not exist
    // Since credit and debit use getBalance, and transfer uses credit and debit, it should cover unregistered accounts there din
    // but to be sure diba regarding if the methods are called properly, etc

    @Test
    void noAccountToGet() {
        //same setup as the successfulGetBalance but with a fake id
        var repo = new AccountRepository();
        String faker = "Adriana";
        var balance = new BalanceService(repo);

        Assertions.assertNull(balance.getBalance(faker));
    }

    @Test
    void notExistingDebitAccount() {
        var repo = new AccountRepository();
        String faker = "Adriana";
        var balance = new BalanceService(repo);
        Double amount = 1.0;

        Assertions.assertNull(balance.debit(faker, amount));
    }

    @Test
    void notExistingCreditAccount() {
        var repo = new AccountRepository();
        String faker = "Adriana";
        var balance = new BalanceService(repo);
        Double amount = 1.0;

        Assertions.assertNull(balance.credit(faker, amount));
    }

    @Test
    void notExistingTransferAccounts() {
        var repo = new AccountRepository();
        String from = "Loreine";
        String to = "Adriana";
        var balance = new BalanceService(repo);
        Double amount = 0.0;

        Assertions.assertNull(balance.transfer(from, to, amount));
    }

    // II. Unsuccessful tests: zero amount
    // While sa GCash app mismo it doesn't allow for zero amount transactions, para lang alam mo how it behaves
    @Test
    void zeroAmountDebit() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = 0.0;

        Assertions.assertEquals(123.4, balance.debit(id, amount));
    }

    @Test
    void zeroAmountCredit() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = 0.0;

        Assertions.assertEquals(123.4, balance.credit(id, amount));
    }

    @Test
    void zeroAmountTransfer() {
        var repo = new AccountRepository();
        String from = repo.createAccount("Loreine", 123.4);
        String to = repo.createAccount("Adriana", 100.0);
        var balance = new BalanceService(repo);
        Double amount = 0.0;

        Assertions.assertEquals(100.0, balance.transfer(from, to, amount));
    }

    // III. Unsuccessful tests: negative amount
    @Test
    void amountIsNegativeDebit() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = -0.01;

        Assertions.assertNull(balance.debit(id, amount));
    }

    @Test
    void amountIsNegativeCredit() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = -0.01;

        Assertions.assertNull(balance.credit(id, amount));
    }

    @Test
    void amountIsNegativeTransfer() {
        var repo = new AccountRepository();
        String from = repo.createAccount("Loreine", 123.4);
        String to = repo.createAccount("Adriana", 100.0);
        var balance = new BalanceService(repo);
        Double amount = -0.01;

        Assertions.assertNull(balance.transfer(from, to, amount));
    }

    //IV. Unsuccessful tests: other scenarios
    @Test
    void debitAmountIsBiggerThanBalance() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4); //creating a test account
        var balance = new BalanceService(repo);
        Double amount = 567.8;

        Assertions.assertNull(balance.debit(id, amount));
    }

    @Test
    void transferAmountIsMoreThanBalance() {
        var repo = new AccountRepository();
        String from = repo.createAccount("Loreine", 123.4);
        String to = repo.createAccount("Adriana", 100.0);
        var balance = new BalanceService(repo);
        Double amount = 1000.0;

        Assertions.assertNull(balance.transfer(from, to, amount));
    }

    @Test
    void transferringToSameAccount() {
        var repo = new AccountRepository();
        String id = repo.createAccount("Loreine", 123.4);
        var balance = new BalanceService(repo);
        Double amount = 1.0;

        Assertions.assertNull(balance.transfer(id, id, amount));
    }

}