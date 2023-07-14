////package com.example.ATM.user;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////
////import com.example.ATM.account.Account;
////
////import jakarta.persistence.Embedded;
////import jakarta.servlet.http.HttpSession;
////
////@Controller
////@RequestMapping("/user")
////public class UserController {
////
////    @Autowired
////    private UserRepository userRepository;
////
////    //////////////////////////////////////Embeded account entity for Id and Account number
////    @Embedded
////    private Account account;
////    
////    public Account getAccount() {
////        return account;
////    }
////    
////    @GetMapping("/register")
////    public String showRegistrationForm(Model model) {
////        model.addAttribute("user", new UserInfo());
////        return "register";
////    }
////
////    @PostMapping("/register")
////    public String registerUser(UserInfo userInfo) {
////        userRepository.save(userInfo);
////        return "redirect:/user/registration-successful";
////    }
////
////    @GetMapping("/registration-successful")
////    public String showRegistrationSuccessfulPage() {
////        return "registration_successful";
////    }
////    @GetMapping("/login")
////    public String showLoginPage() {
////        return "login";
////    }
////    @GetMapping("/logout")
////    public String logout(HttpSession session) {
////    	// Invalidate the session and clear all attributes
////    	session.invalidate();
////    	return "redirect:/user/login";
////    }
////    @PostMapping("/login")
////    public String login(String name, String password, HttpSession session, Model model) {
////        UserInfo user = userRepository.findByName(name);
////
////        if (user != null && user.getPassword().equals(password)) {
////            // Successful login
////        	session.setAttribute("user", user);
////            model.addAttribute("username", name);
////            return "redirect:/user/dashboard";
////        } else {
////        	// Invalid credentials
////            model.addAttribute("error", "Invalid username or password");
////            return "login";
////        }
////    }
////
////    @GetMapping("/dashboard")
////    public String showDashboardPage(HttpSession session, Model model) {
////        UserInfo user = (UserInfo) session.getAttribute("user");
////        if (user != null) {
////            model.addAttribute("username", user.getName());
////            return "dashboard";
////        } else {
////            return "redirect:/user/login";
////        }
////    }
////}
////
//package com.example.ATM.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.ATM.account.Account;
//
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new UserInfo());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(UserInfo userInfo) {
//        Account account = new Account();
//        account.setName(userInfo.getName());
//        account.setAccountNumber(userInfo.getAccountNumber());
//        account.setRemainingBalance(0.0);
//
//        userInfo.setAccount(account); // Associate account with user info
//
//        userRepository.save(userInfo);
//
//        return "redirect:/user/dashboard";
//    }
//
////    @GetMapping("/registration-successful")
////    public String showRegistrationSuccessfulPage() {
////        return "registration_successful";
////    }
//
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        // Invalidate the session and clear all attributes
//        session.invalidate();
//        return "redirect:/user/login";
//    }
//
//    @PostMapping("/login")
//    public String login(String name, String password, HttpSession session, Model model) {
//        UserInfo user = userRepository.findByName(name);
//
//        if (user != null && user.getPassword().equals(password)) {
//            // Successful login
//            session.setAttribute("user", user);
//            System.out.println("User object stored in session: " + user);
//            model.addAttribute("username", name);
//            return "redirect:/user/dashboard";
//        } else {
//            // Invalid credentials
//            model.addAttribute("error", "Invalid username or password");
//            return "login";
//        }
//    }
//
//    @GetMapping("/dashboard")
//    public String showDashboardPage(HttpSession session, Model model) {
//        UserInfo user = (UserInfo) session.getAttribute("user");
//        if (user != null) {
//            model.addAttribute("username", user.getName());
//            return "dashboard";
//        } else {
//            return "redirect:/user/login";
//        }
//    }
//}

package com.example.ATM.user;

import com.example.ATM.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserInfo());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(UserInfo userInfo) {
        Account account = new Account();
        account.setName(userInfo.getName());
        account.setAccountNumber(userInfo.getAccountNumber());
        account.setRemainingBalance(0.0);

        userInfo.setAccount(account); // Associate account with user info

        userRepository.save(userInfo);

        return "redirect:/user/dashboard";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session and clear all attributes
        session.invalidate();
        return "redirect:/user/login";
    }

    @PostMapping("/login")
    public String login(String name, String password, HttpSession session, Model model) {
        UserInfo user = userRepository.findByName(name);

        if (user != null && user.getPassword().equals(password)) {
            // Successful login
            session.setAttribute("user", user);
            System.out.println("User object stored in session: " + user);
            model.addAttribute("username", name);
            return "redirect:/user/dashboard";
        } else {
            // Invalid credentials
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(HttpSession session, Model model) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getName());
            return "dashboard";
        } else {
            return "redirect:/user/login";
        }
    }
}

