package com.example.bank.service;

import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import com.example.bank.services.BankAccountService;
import com.example.bank.services.BankAccountServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;



public class BankAccountServiceTest {

    BankAccountService bankAccountService = new BankAccountServiceImpl();

    @Test
    @DisplayName("Should add 100 euros to an account with an initial balance of 0")
    public void testDepositAddsAmountToEmptyAccount() throws DepositAcountException {
        // Arrange
        BankAccount account = new BankAccount(); // Initial balance of 0
        BigDecimal depositAmount = new BigDecimal("100.00");

        // Act
        bankAccountService.deposit(account, depositAmount);

        // Assert
        assertEquals(new BigDecimal("100.00"), account.getBalance(),
                "The account balance should reflect the deposited amount of 100 euros.");
    }

    @Test
    @DisplayName("Should add 50 euros to an account with an initial balance of 100")
    public void testDepositAddsAmountToAccountWithBalance100() throws DepositAcountException {
        // Arrange
        BankAccount account = new BankAccount(); // Initial balance of 0
        account.setBalance(new BigDecimal("100.00"));
        BigDecimal depositAmount = new BigDecimal("50.00");

        // Act
        bankAccountService.deposit(account, depositAmount);

        // Assert
        assertEquals(new BigDecimal("150.00"), account.getBalance(),
                "The account balance should reflect the deposited amount of 150 euros.");
    }

    @Test
    @DisplayName("Should throw an error when a negative deposit amount is provided")
    public void testDepositThrowsErrorForNegativeAmount() {
        // Arrange
        BankAccount account = new BankAccount(); // Initial balance of 0
        account.setBalance(new BigDecimal("100.00"));
        BigDecimal depositAmount = new BigDecimal("-50.00");

        // Act
        assertThrows(DepositAcountException.class,
                () -> bankAccountService.deposit(account, depositAmount),
                "A deposit of negative Amount should not be allowed.");
    }
    @Test
    @DisplayName("Should throw an error when a deposit amount of zero is provided")
    public void testDepositThrowsErrorForZeroAmount() {
        BankAccount account = new BankAccount(); // Initial balance of 0
        account.setBalance(new BigDecimal("100.00"));
        BigDecimal depositAmount = new BigDecimal("00");

        // Act
        assertThrows(DepositAcountException.class,
                () -> bankAccountService.deposit(account, depositAmount),
                "A deposit of zero should not be allowed.");
    }
}
