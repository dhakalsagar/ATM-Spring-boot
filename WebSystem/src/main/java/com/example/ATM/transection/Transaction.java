package com.example.ATM.transection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.ATM.account.Account;
import com.example.ATM.user.UserInfo;


@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

   
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    
    private LocalDateTime transactionDate;

    
    public String getFormattedTransactionDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return transactionDate.format(formatter);
    }

    
    private double amount;

    private String accountName;
    private String accountNumber;
    
    private String senderName;
    private String senderAccountNumber;
    
    private String receiverName;
    private String receiverAccountNumber;
    

    @PrePersist
    private void setTransactionDetails() {
        if (account != null) {
            this.accountName = account.getName();
            this.accountNumber = account.getAccountNumber();
        }
        this.transactionDate = LocalDateTime.now();
    }
}

