package com.example.ATM.account;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ATM.transection.Transaction;
import com.example.ATM.transection.TransactionType;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public void deposit(String accountNumber, double amount) {
        Account account = accountRepository.findAccountByNumber(accountNumber); 
        if (account == null) {
            throw new IllegalArgumentException("Invalid account number");
        }
        account.deposit(amount);

        Transaction transaction = new Transaction();
        transaction.setAccount(account); // Set the account for the transaction
        transaction.setSenderName("self");
        transaction.setSenderAccountNumber("-");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setReceiverName("-");
        transaction.setReceiverAccountNumber("-");
        account.addTransaction(transaction);

        accountRepository.save(account);
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findAccountByNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Invalid account number");
        }

        if (!account.withdraw(amount)) {
            return false; // Insufficient balance
        }

        Transaction transaction = new Transaction();
        transaction.setSenderName("自己");
        transaction.setSenderAccountNumber("自己");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setReceiverName("自己");
        transaction.setReceiverAccountNumber("自己");
        account.addTransaction(transaction);

        accountRepository.save(account);
        return true; // Withdrawal successful
    }

    public boolean transfer(String senderAccountNumber, String recipientAccountNumber, double amount) {
        Account senderAccount = accountRepository.findAccountByNumber(senderAccountNumber);
        Account recipientAccount = accountRepository.findAccountByNumber(recipientAccountNumber);
        
        if (senderAccount == null || recipientAccount == null) {
            throw new IllegalArgumentException("Invalid sender or recipient account number");
        }
        
        if (senderAccount.getRemainingBalance() < amount) {
            return false; // Insufficient balance
        }

        senderAccount.withdraw(amount);
        recipientAccount.deposit(amount);

        Transaction senderTransaction = new Transaction();
        senderTransaction.setAccount(senderAccount); // Set the account for the sender's transaction
        senderTransaction.setTransactionDate(LocalDateTime.now());
        senderTransaction.setTransactionType(TransactionType.TRANSFER);
        senderTransaction.setAmount(-amount);
        senderTransaction.setSenderName("自己");
        senderTransaction.setSenderAccountNumber("自己");
        senderTransaction.setReceiverName(recipientAccount.getName());
        senderTransaction.setReceiverAccountNumber(recipientAccount.getAccountNumber());
        senderAccount.addTransaction(senderTransaction);

        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setAccount(recipientAccount); // Set the account for the recipient's transaction
        recipientTransaction.setTransactionDate(LocalDateTime.now());
        recipientTransaction.setTransactionType(TransactionType.RECEIVED);
        recipientTransaction.setAmount(amount);
        recipientTransaction.setReceiverName("自己");
        recipientTransaction.setReceiverAccountNumber("自己");
//        recipientTransaction.setSenderName(senderTransaction.getName());
//        recipientTransaction.setSenderAccountNumber(senderTransaction.getAccountNumber());
        
        recipientTransaction.setSenderName(senderAccount.getName()); // Update this line
        recipientTransaction.setSenderAccountNumber(senderAccount.getAccountNumber()); // Update this line
        
        recipientAccount.addTransaction(recipientTransaction);

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        return true; // Transfer successful
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByNumber(accountNumber);
    }
}
