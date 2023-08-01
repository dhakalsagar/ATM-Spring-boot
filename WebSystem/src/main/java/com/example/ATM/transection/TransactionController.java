package com.example.ATM.transection;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ATM.account.Account;
import com.example.ATM.account.AccountService;
import com.example.ATM.user.UserInfo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/transactions")
public class TransactionController {
    private final AccountService accountService;

    @Autowired
    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/history")
    public String showTransactionHistory(HttpSession session, Model model) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login"; // Redirect to the login page or handle unauthorized access accordingly
        }

        Account account = user.getAccount();
        List<Transaction> transactions = account.getTransactions();
        
        Collections.sort(transactions, Comparator.comparing(Transaction::getTransactionDate).reversed());
        model.addAttribute("transactions", transactions);
        
        double totalBalance = account.getRemainingBalance();
        model.addAttribute("totalBalance", totalBalance);
        
        return "checkSession"; // Modify the view name accordingly
    }
    
//
//    @PostMapping("/deposit")
//    public ResponseEntity<String> deposit(@RequestParam String accountNumber, @RequestParam double amount, HttpSession session) {
//        Account account = accountService.getAccountByAccountNumber(accountNumber);
//        if (account != null) {
//            accountService.deposit(accountNumber, amount);
//            return ResponseEntity.ok("Deposit successful");
//        }
//        return ResponseEntity.ok("Invalid account");
//    }
//
//    @PostMapping("/withdraw")
//    public ResponseEntity<String> withdraw(@RequestParam String accountNumber, @RequestParam double amount, HttpSession session) {
//        Account account = accountService.getAccountByAccountNumber(accountNumber);
//        if (account != null) {
//            boolean withdrawalSuccessful = accountService.withdraw(accountNumber, amount);
//            if (withdrawalSuccessful) {
//                return ResponseEntity.ok("Withdrawal successful");
//            } else {
//                return ResponseEntity.ok("Insufficient balance");
//            }
//        }
//        return ResponseEntity.ok("Invalid account");
//    }
//
//    @PostMapping("/transfer")
//    public ResponseEntity<String> transfer(@RequestParam String senderAccountNumber, @RequestParam String receiverAccountNumber, @RequestParam double amount, HttpSession session) {
//        Account senderAccount = accountService.getAccountByAccountNumber(senderAccountNumber);
//        Account receiverAccount = accountService.getAccountByAccountNumber(receiverAccountNumber);
//
//        if (senderAccount != null && receiverAccount != null) {
//            boolean transferSuccessful = accountService.transfer(senderAccountNumber, receiverAccountNumber, amount);
//            if (transferSuccessful) {
//                return ResponseEntity.ok("Transfer successful");
//            } else {
//                return ResponseEntity.ok("Insufficient balance");
//            }
//        }
//        return ResponseEntity.ok("Invalid accounts");
//    }
}
//
