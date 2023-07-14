package com.example.ATM.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void deposit(long accountId, double amount) {
        Account account = entityManager.find(Account.class, accountId);
        
        System.out.println(account);
        if (account != null) {
            account.deposit(amount);
            entityManager.merge(account);
        }
    }
    
    public void withDraw(long accountId, double amount) {
        Account account = entityManager.find(Account.class, accountId);
        
        System.out.println(account);
        if (account != null) {
            account.withDraw(amount);
            entityManager.merge(account);
        }
    }
}


// @PersistenceContext -> The persistence context is the first-level cache where all the entities are fetched from the database or saved to the database