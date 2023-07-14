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
    public ResponseEntity<String> deposit(@RequestParam long accountId, @RequestParam double amount, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        accountService.deposit(accountId, amount);
        return ResponseEntity.ok("Deposit successful");
    }
    
    @GetMapping("/withdraw")
    public String showWithDrawform(HttpSession session) {
    	UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/login";
        }
        return "withdraw";
    }
    @PostMapping("/withdraw")
	public ResponseEntity<String> widhDraw(@RequestParam long accountId, @RequestParam double amount, HttpSession session) {
		  UserInfo user = (UserInfo) session.getAttribute("user");
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
	        }

	        accountService.withDraw(accountId, amount);
	        return ResponseEntity.ok("withdraw successful");
	    }
}
