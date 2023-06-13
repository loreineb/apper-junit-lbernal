package com.gcash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountRepository {
    private final List<Account> accounts = new ArrayList<>();

    public String createAccount(String name, Double initialBalance) {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, name, initialBalance);

        accounts.add(account);

        return id;
    }

    public Account getAccount(String id) {
        return accounts
                .stream()
                .filter(account -> id.equals(account.getId()))
                .findFirst()
                .orElse(null);
    }

    public void deleteAccount(String id) {
        accounts
                .stream()
                .filter(account -> id.equals(account.getId()))
                .findFirst()
                .ifPresent(accounts::remove);
    }

    public Integer getNumberOfAccounts() {
        return accounts.size();
    }

    // No test. Create one
    public boolean noRegisteredAccount() {
        return accounts.isEmpty();
    }

    public List<String> getAllAccountNames() {
        return accounts
                .stream()
                .map(Account::getName)
                .collect(Collectors.toList());
    }

    void deleteAllAccounts() {
        accounts.clear();
    }
}