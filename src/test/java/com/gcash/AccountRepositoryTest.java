package com.gcash;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryTest {

    @Test
    void successfulAccountCreation() {
        // Setup
        AccountRepository repository = new AccountRepository();

        // Kick
        String accountId = repository.createAccount("Orvyl", 89.9);

        // Verify
        Assertions.assertEquals(1, repository.getNumberOfAccounts());
        Assertions.assertEquals("Orvyl", repository.getAccount(accountId).getName());
        Assertions.assertNotNull(accountId);
    }

    @Test
    void successfulGetAccount() {
        AccountRepository repository = new AccountRepository();

        String accountId = repository.createAccount("Orvyl", 89.9);

        Assertions.assertEquals("Orvyl", repository.getAccount(accountId).getName());
        Assertions.assertEquals(89.9, repository.getAccount(accountId).getBalance());
        Assertions.assertNull(repository.getAccount("randomid"));
    }

    @Test
    void successfulDelete() {
        //Setup
        AccountRepository repository = new AccountRepository();
        String id = repository.createAccount("Orvyl", 89.9);

        //Kick
        repository.deleteAccount(id);

        //Verify
        Assertions.assertEquals(0, repository.getNumberOfAccounts());
    }

    @Test
    void successfulGetNumberOfAccounts() {
        //Setup and kick
        AccountRepository repository = new AccountRepository();
        String id0 = repository.createAccount("Orvyl", 89.9);
        String id1 = repository.createAccount("Orvyl", 89.9);
        String id2 = repository.createAccount("Orvyl", 89.9);
        String id3 = repository.createAccount("Orvyl", 89.9);

        //Verify
        Assertions.assertEquals(4, repository.getNumberOfAccounts());

        //Setup
        repository.deleteAccount(id0);

        //Verify
        Assertions.assertEquals(3, repository.getNumberOfAccounts());
    }

    @Test
    void noRegisteredAccount() {
        AccountRepository accountRepository = new AccountRepository();

        Assertions.assertTrue(accountRepository.noRegisteredAccount());
    }

    @Test
    void getAllAccountNames() {
        AccountRepository accountRepository = new AccountRepository();
        accountRepository.createAccount("Orvyl", 100.0);
        accountRepository.createAccount("Eishi", 100.0);
        accountRepository.createAccount("James", 100.0);
        accountRepository.createAccount("Janet", 100.0);
        accountRepository.createAccount("John", 100.0);

        List<String> allAccountNames = accountRepository.getAllAccountNames();

        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Orvyl");
        expectedNames.add("Eishi");
        expectedNames.add("James");
        expectedNames.add("Janet");
        expectedNames.add("John");

        Assertions.assertIterableEquals(expectedNames, allAccountNames);
    }
}