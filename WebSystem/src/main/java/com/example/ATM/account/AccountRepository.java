package com.example.ATM.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Spring Data JPA will automatically generate the implementation for this method
    Account findByAccountNumber(String accountNumber);
    // You can add other custom query methods here, if needed.
}
