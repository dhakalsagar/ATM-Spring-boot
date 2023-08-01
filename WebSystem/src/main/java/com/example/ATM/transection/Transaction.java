package com.example.ATM.transection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.ATM.account.Account;

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

    private String transactionDate; // Changed to String

    private double amount;

    private String accountName;
    private String accountNumber;

    private String senderName;
    private String senderAccountNumber;

    private String receiverName;
    private String receiverAccountNumber;

    public String getFormattedTransactionDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(transactionDate, formatter);
        return localDateTime.format(formatter);
    }

    @PrePersist
    private void setTransactionDetails() {
        if (account != null) {
            this.accountName = account.getName();
            this.accountNumber = account.getAccountNumber();
        }
        this.transactionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
