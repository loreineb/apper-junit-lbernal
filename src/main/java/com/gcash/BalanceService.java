package com.gcash;

public class BalanceService {
    private final AccountRepository repo;
    //use private final if you want it to be immutable and cannot be accessed diretso
    //I don't 100% get it yet, pero sabi it makes it easier to debug kasi since immutable siya, di nagbabago yung value niya
    //since constant din yung value niya, pag pinasa-pasa na siya, there's no risk of it changing somehow on the way, I think

    public BalanceService (AccountRepository repository) {
        this.repo = repository;
    }
    //rather than create an instance of AccountRepository sa getBalance like what I did initially, separate mo siya
    //para din alam ng lahat ng nasa BalanceService na pag repository yung usapan, it's referring to this one

    /**
     * NOTE: You are expected to use one repository instance in all methods, not one repository per method.
     *
     */

    public Double getBalance(String id) {
        //initially:
        // 1. I created an instance of AccountRepository here in getBalance
        // 2. In the test, I created another instance of AccountRepository in the setup so I could create a user to get their balance
        // 3. the test ended up failing kasi nagdodouble yung AccountRepository
            //so what would happen is iba yung repo na ginagamit ni getBalance and the repo which I created the test user in is magkaiba

        if (!id.isEmpty()) { //checking if hindi empty yung pinasok
            Account account = repo.getAccount(id);
            //using the existing method in AccountRepository to retrieve the account associated with the id
            if (account != null) { //if hindi null si account, it means na may account nga na ganun with the corresponding id
                return account.balance(); //eto na mismo yung pag get ng balance, after the two conditions have been met
            }
        }
        return null;
    }

    public Double debit(String id, Double amount) {
        //debit is to bawas
        //I can just use getBalance above
        var bal = getBalance(id);

        if (bal == null) {
            return null;
            //order matters here, this if condition used to be at the bottom
            //if nauna kasi si amount <= bal, magkakaron ng null pointer error kasi it can't compare with null
        }
        if (amount > 0 && amount <= bal) {
            Double debitBalance = bal - amount;
            return debitBalance;
        }
        if (amount == 0) {
            return bal;
        }
        return null; //put null here pa rin just in case merong condition na di pa naiisip

        //I changed from public void debit to public Double debit so I can use debit in transfer
    }

    public Double credit(String id, Double amount) {
        //credit is to dagdag
        var bal = getBalance(id);

        if (bal == null) {
            return null;
        }
        if (amount > 0) {
            Double creditBalance = bal + amount;
            //if I accidentally change this to getBalance(id) - amount, the tests will catch it
            //so I can mess with the code with a safeguard of sorts
            return creditBalance;
        }
        if (amount == 0) {
            return bal;
        }
        return null;
    }

    public Double transfer(String from, String to, Double amount) {
        //I'm assuming from and to here are ids
        //can reuse debit and credit here

        if (!from.equals(to) && debit(from,amount) != null) {
            //no need to put in the if condition for transfer regarding zero values kasi debit and credit have it
            debit(from, amount);
            Double recipientBalance = credit(to, amount);
            return recipientBalance;
        }
        if (getBalance(from) == null || getBalance(to) == null) {
            return null;
        }
        return null;
    }
}