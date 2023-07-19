package com.example.ATM.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ATM.transection.Transaction;
import com.example.ATM.user.UserInfo;

@Repository
@Transactional
public class AccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void deposit(String accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            account.deposit(amount);
            entityManager.merge(account);
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            entityManager.merge(account);
        }
    }

    public Account findAccountByNumber(String accountNumber) {
        String jpql = "SELECT a FROM Account a WHERE a.accountNumber = :accountNumber";
        return entityManager.createQuery(jpql, Account.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();
    }
    public void save(Account account) {
        entityManager.persist(account);
    }
    public void addTransaction(Account account, Transaction transaction) {
        account.addTransaction(transaction);
        entityManager.merge(account);
    }

//    public Account findByName(String name) {
//        String jpql = "SELECT a FROM Account a WHERE a.name = :name";
//        return entityManager.createQuery(jpql, Account.class)
//                .setParameter("name", name)
//                .getSingleResult();
//    }
}


// @PersistenceContext -> The persistence context is the first-level cache where all the entities are fetched from the database or saved to the database