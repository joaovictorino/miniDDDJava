package com.bank.account.model.service;

import com.bank.account.model.Account;
import com.bank.account.model.Receipt;

public class TransferMoney {
    public Receipt transfer(Account accountFrom, Account accountTo, double value) throws Exception {
        if(accountFrom == null 
            || accountTo == null)
            throw new Exception("account should be not null");

        accountFrom.withDraw(value);
        accountTo.deposit(value);
        return new Receipt();
    }
}
