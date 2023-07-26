package com.example.ATM.account;

import com.example.ATM.transection.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void deposit(String accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            account.deposit(amount);
            save(account);
        }
    }

    @Override
    public void withdraw(String accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            save(account);
        }
    }

    @Override
    public Account findAccountByNumber(String accountNumber) {
        // Implementing directly using Spring Data JPA's built-in method
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void addTransaction(Account account, Transaction transaction) {
        account.addTransaction(transaction);
        save(account);
    }
}
