package com.bank.account.model;

public class Account {
    private AccountNumber numberAccount;
    private double balance;

    public Account(AccountNumber numberAccount, double balance) {
        this.numberAccount = numberAccount;
        this.balance = balance;
    }

    public void withDraw(double value) throws Exception {
        if ((this.balance - value) < 0) {
            throw new Exception("no balance available");
        }
        this.balance -= value;
    }

    public void deposit(double value) {
        this.balance += value;
    }

    public double getBalance() {
        return this.balance;
    }

    public AccountNumber getAccountNumber() {
        return this.numberAccount;
    }
}
