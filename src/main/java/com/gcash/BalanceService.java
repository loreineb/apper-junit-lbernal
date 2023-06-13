package com.gcash;

import java.util.Objects;

public class BalanceService {
    private final AccountRepository accountRepository;

    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Double getBalance(String id) throws AccountNotFoundException {
        Account foundAccount = accountRepository.getAccount(id);
        if (Objects.isNull(foundAccount)) {
            throw new AccountNotFoundException("Account " + id + " not found");
        }
        return foundAccount.getBalance();
    }

    public void debit(String id, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        Account foundAccount = accountRepository.getAccount(id);
        if (Objects.isNull(foundAccount)) {
            throw new AccountNotFoundException("Account " + id + " not found");
        }

        if (foundAccount.getBalance() >= amount) {
            foundAccount.setBalance(foundAccount.getBalance() - amount);
        } else {
            throw new InsufficientBalanceException("Insufficient balance!");
        }
    }

    public void credit(String id, Double amount) {
        Account foundAccount = accountRepository.getAccount(id);
        if (Objects.nonNull(foundAccount)) {
            foundAccount.setBalance(foundAccount.getBalance() + amount);
        }
    }

    public void transfer(String from, String to, Double amount) throws InsufficientBalanceException, AccountNotFoundException {
        debit(from, amount);
        credit(to, amount);
    }
}