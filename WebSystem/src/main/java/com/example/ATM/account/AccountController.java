package com.example.ATM.account;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String deposit(@RequestParam double amount, HttpSession session, Model model) {
        UserInfo user = (UserInfo) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("errorMessage", "Authentication required");
            return "error-page";
        }

        String accountNumber = user.getAccountNumber();
        try {
            accountService.deposit(accountNumber, amount);
            model.addAttribute("transactionType", "Deposit");
            model.addAttribute("success", true);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("transactionType", "Deposit");
            model.addAttribute("success", false);
        }

        model.addAttribute("amount", amount);
        return "success";
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
    public String withdraw(@RequestParam double amount, HttpSession session, Model model) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
        	model.addAttribute("errorMessage", "Authentication Required");
            return "error-page";
        }

        String accountNumber = user.getAccountNumber();
        boolean withdrawalSuccessful = accountService.withdraw(accountNumber, amount);
        if (withdrawalSuccessful) {
           model.addAttribute("transactionType", "Withdraw");
           model.addAttribute("success", true);
           return "success";
        } else {
        	model.addAttribute("transactionType", "Withdraw");
            model.addAttribute("success", false);
        	return "success";
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
    public String transfer(@RequestParam String accountNumber, @RequestParam double amount, HttpSession session, Model model) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
        	model.addAttribute("errorMessage", "Authentication Required");
            return "error-page";
        }
        
        String senderAccountNumber = user.getAccountNumber();
        boolean transferSuccessful = accountService.transfer(senderAccountNumber, accountNumber, amount);
        model.addAttribute("transactionType", "Transfer");
        model.addAttribute("amount", amount);

        if (transferSuccessful) {
            model.addAttribute("success", true);
            return "success";
        } else {
            model.addAttribute("success", false);
            return "success";
        	}
     }
    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error-page"; // Replace "error-page" with the name of your error page template
        }
    }

}
