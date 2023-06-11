package com.gcash;

public class BalanceService {

    /**
     * NOTE: You are expected to use one repository instance in all methods, not one repository per method.
     *
     */

    public Double getBalance(String id) {
        var repo = new AccountRepository(); //creating an instance of the class
        //can use var nalang instead of typing the whole class name, I watched in a video na Java can infer naman daw
        if (!id.isEmpty()) { //checking if hindi empty yung pinasok
            Account account = repo.getAccount(id);
            //using the existing method in AccountRepository to retrieve the account associated with the id
            if (account != null) { //if hindi null si account, it means na may account nga na ganun with the corresponding id
                return account.balance(); //eto na mismo yung pag get ng balance, after the two conditions have been met
            }
        }
        return null;
    }

    public double debit(String id, Double amount) {
        //debit is to bawas
        //I can just use getBalance above I think
        double debitBalance = getBalance(id) - amount;
        return debitBalance;
        //how come debit is void while credit is double?
        //I changed from public void debit to public double debit so I can use debit in transfer
    }

    public double credit(String id, Double amount) {
        //credit is to dagdag
        double creditBalance = getBalance(id) + amount;
        return creditBalance;
    }

    public void transfer(String from, String to, Double amount) {
        //I'm assuming from and to here are ids
        double senderBalance = debit(from, amount);
        double recipientBalance = credit(to, amount);

    }
}