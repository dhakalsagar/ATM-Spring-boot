package com.example.ATM.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ATM.account.Account;
import com.example.ATM.account.AccountRepository;

@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserInfoService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public UserInfo saveUserInfo(UserInfo userInfo) throws DuplicateAccountNumberException {
        String accountNumber = userInfo.getAccountNumber();
        UserInfo existingUserInfo = userRepository.findByAccountNumber(accountNumber);
        if (existingUserInfo != null) {
            throw new DuplicateAccountNumberException("Account number already exists");
        }

        Account account = new Account();
        account.setName(userInfo.getName());
        account.setAccountNumber(accountNumber);
        account.setRemainingBalance(0.0);

        userInfo.setAccount(account);
        userInfo = userRepository.save(userInfo);
        return userInfo;
    }
}
