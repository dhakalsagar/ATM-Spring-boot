package com.example.ATM.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void deposit(long accountId, double amount) {
        accountRepository.deposit(accountId, amount);
    }
    public void withDraw(long accountId, double amount) {
    	accountRepository.withDraw(accountId, amount);
    }
}

