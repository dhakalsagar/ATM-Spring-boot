package com.example.ATM.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ATM.user.UserInfo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/deposit")
    public String showDepositForm(HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        return "deposit";
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam double amount, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        String accountNumber = user.getAccountNumber();
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    
    @GetMapping("/withdraw")
    public String showWithdrawForm(HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam double amount, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        String accountNumber = user.getAccountNumber();
        boolean withdrawalSuccessful = accountService.withdraw(accountNumber, amount);
        if (withdrawalSuccessful) {
            return ResponseEntity.ok("Withdrawal successful");
        } else {
            return ResponseEntity.ok("Insufficient balance or invalid account");
        }

    }
    @GetMapping("/transfer")
    public String showTransferForm(HttpSession session, Model model) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("user", user);
        return "transfer";
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String accountNumber, @RequestParam double amount, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        String senderAccountNumber = user.getAccountNumber();
        boolean transferSuccessful = accountService.transfer(senderAccountNumber, accountNumber, amount);
        if (transferSuccessful) {
            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.ok("Insufficient balance or invalid account");
        }
    }

}
