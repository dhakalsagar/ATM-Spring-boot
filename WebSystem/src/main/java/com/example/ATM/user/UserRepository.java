package com.example.ATM.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByName(String name);
    UserInfo findByAccountNumber(String accountNumber);
}

