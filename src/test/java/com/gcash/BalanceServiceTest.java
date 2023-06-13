package com.gcash;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceServiceTest {

    AccountRepository accountRepository;
    BalanceService balanceService;

    @BeforeEach
    void setup() {
        System.out.println("Setting up...");
        accountRepository = new AccountRepository();
        balanceService = new BalanceService(accountRepository);
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up...");
        accountRepository.deleteAllAccounts();
    }

    @BeforeAll
    static void globalSetup() {
        System.out.println("Global setup");
    }

    @AfterAll
    static void globalCleaning() {
        System.out.println("Global cleaning");
    }

    @Test
    void testGetBalance() throws AccountNotFoundException {
        double initialBalance = 1000.0;
        String id = accountRepository.createAccount("Orvyl", initialBalance);

        Double balance = balanceService.getBalance(id);

        Assertions.assertEquals(initialBalance, balance);
    }

    @Test
    void testGetBalanceAccountNotFound() {
        double initialBalance = 1000.0;
        accountRepository.createAccount("Orvyl", initialBalance);

        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.getBalance("random id"));
    }

    @Test
    void testDebit() throws InsufficientBalanceException, AccountNotFoundException {
        double initialBalance = 1000.0;
        String id = accountRepository.createAccount("Orvyl", initialBalance);

        double debitedAmount = 50.0;
        balanceService.debit(id, debitedAmount);

        Double currentBalance = accountRepository.getAccount(id).getBalance();

        Assertions.assertEquals(initialBalance - debitedAmount, currentBalance);
    }

    @Test
    void testDebitInsufficientBalance() {
        double initialBalance = 1000.0;
        String id = accountRepository.createAccount("Orvyl", initialBalance);

        double debitedAmount = 50_000.0;
        Assertions.assertThrows(InsufficientBalanceException.class, () -> balanceService.debit(id, debitedAmount));
    }

    @Test
    void testDebitAccountNotFound() {
        double initialBalance = 1000.0;
        String id = accountRepository.createAccount("Orvyl", initialBalance);

        double debitedAmount = 50_000.0;
        Assertions.assertThrows(AccountNotFoundException.class, () -> balanceService.debit("random id", debitedAmount));
    }

    @Test
    void testTransfer() throws InsufficientBalanceException, AccountNotFoundException {
        double initialBalance = 1000.0;
        String id0 = accountRepository.createAccount("Orvyl", initialBalance);
        String id1 = accountRepository.createAccount("Eishi", initialBalance);

        double transferAmount = 50.0;
        balanceService.transfer(id0, id1, transferAmount);

        Assertions.assertAll(
                () -> Assertions.assertEquals(initialBalance - transferAmount, accountRepository.getAccount(id0).getBalance()),
                () -> Assertions.assertEquals(initialBalance + transferAmount, accountRepository.getAccount(id1).getBalance())
        );
    }
}