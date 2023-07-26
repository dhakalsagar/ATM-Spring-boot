package com.example.ATM.account;

import com.example.ATM.transection.Transaction;

public interface AccountDAO {
    void deposit(String accountNumber, double amount);
    void withdraw(String accountNumber, double amount);
    Account findAccountByNumber(String accountNumber);
    void save(Account account);
    void addTransaction(Account account, Transaction transaction);
}
