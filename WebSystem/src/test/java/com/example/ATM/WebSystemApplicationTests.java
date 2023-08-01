package com.example.ATM;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ATM.account.Account;
import com.example.ATM.user.UserInfo;

@SpringBootTest
class WebSystemApplicationTests {

	@Test
    public void testGettersAndSetters() {
        // Arrange
        UserInfo userInfo = new UserInfo();
        String name = "John";
        String password = "password";
        String accountNumber = "123456789";

        // Act
        userInfo.setName(name);
        userInfo.setPassword(password);
        userInfo.setAccountNumber(accountNumber);

        // Assert
        assertEquals(name, userInfo.getName());
        assertEquals(password, userInfo.getPassword());
        assertEquals(accountNumber, userInfo.getAccountNumber());
    }

    @Test
    public void testAccountUniqueness() {
        // This test will depend on your data access layer (DAO/repository).
        // You can use an in-memory database (e.g., H2) or a test database to test this scenario.
    }

    @Test
    public void testAccountRelationship() {
        // Arrange
        Account mockAccount = Mockito.mock(Account.class);
        UserInfo userInfo = new UserInfo();

        // Act
        userInfo.setAccount(mockAccount);

        // Assert
        assertEquals(mockAccount, userInfo.getAccount());
    }

}


