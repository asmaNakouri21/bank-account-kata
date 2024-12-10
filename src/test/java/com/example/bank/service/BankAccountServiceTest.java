package com.example.bank.service;

import com.example.bank.dao.BankAccountRepository;
import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import com.example.bank.services.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;


public class BankAccountServiceTest {
    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    @Mock
    BankAccountRepository bankAccountRepository;
    private final static Long accountId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should add 100 euros to an account with an initial balance of 0")
    public void testDepositAddsAmountToEmptyAccount() throws DepositAcountException {
        // Arrange
        Optional<BankAccount> mockedAccount = getMockedEmptyAccount();
        when(bankAccountRepository.findById(accountId)).thenReturn(mockedAccount);

        BigDecimal depositAmount = new BigDecimal("100.00");

        // Act
        bankAccountService.deposit(accountId, depositAmount);

        // Assert
        assertEquals(new BigDecimal("100.00"), mockedAccount.get().getBalance(),
                "The account balance should reflect the deposited amount of 100 euros.");
    }

    @Test
    @DisplayName("Should add 50 euros to an account with an initial balance of 100")
    public void testDepositAddsAmountToAccountWithBalance100() throws DepositAcountException {
        // Arrange
        Optional<BankAccount> mockedAccount = getMockedAccount();
        when(bankAccountRepository.findById(accountId)).thenReturn(mockedAccount);

        BigDecimal depositAmount = new BigDecimal("50.29");

        // Act
        bankAccountService.deposit(accountId, depositAmount);

        // Assert
        assertEquals(new BigDecimal("150.29"), mockedAccount.get().getBalance(),
                "The account balance should reflect the deposited amount of 150.29 euros.");
    }

    @Test
    @DisplayName("Should throw an error when a negative deposit amount is provided")
    public void testDepositThrowsErrorForNegativeAmount() {
        // Arrange
        Optional<BankAccount> mockedAccount = getMockedAccount();
        when(bankAccountRepository.findById(accountId)).thenReturn(mockedAccount);

        BigDecimal depositAmount = new BigDecimal("-50.00");

        // Act
        assertThrows(DepositAcountException.class,
                () -> bankAccountService.deposit(accountId, depositAmount),
                "A deposit of negative Amount should not be allowed.");
    }

    @Test
    @DisplayName("Should throw an error when a deposit amount of zero is provided")
    public void testDepositThrowsErrorForZeroAmount() {
        Optional<BankAccount> mockedAccount = getMockedAccount();
        when(bankAccountRepository.findById(accountId)).thenReturn(mockedAccount);
        BigDecimal depositAmount = new BigDecimal("0");

        // Act
        assertThrows(DepositAcountException.class,
                () -> bankAccountService.deposit(accountId, depositAmount),
                "A deposit of zero should not be allowed.");
    }

    @Test
    public void testGetAccountById_ReturnsAccount_WhenAccountExists() throws DepositAcountException {
        // Arrange
        Optional<BankAccount> mockedAccount = getMockedAccount();
        when(bankAccountRepository.findById(accountId)).thenReturn(mockedAccount);

        // Act
        BankAccount result = bankAccountService.getAccountById(accountId);

        // Assert
        assertNotNull(result, "accountResult is not null");
        assertEquals(accountId, result.getId(), "the acount ID is 1L");
        assertEquals(new BigDecimal("100"), result.getBalance(),
                "The account balance is 100.00");

    }

    @Test
    @DisplayName("Should throw an error when the account Id does not Exist")
    public void testGetAccountById_ThrowsException_WhenAccountDoesNotExist() {
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(DepositAcountException.class,
                () -> bankAccountService.getAccountById(accountId),
                "Account does not exist");
    }

    private Optional<BankAccount> getMockedAccount() {
        return Optional.of(BankAccount.builder()
                .id(1L)
                .balance(new BigDecimal(100.00))
                .build());
    }

    private Optional<BankAccount> getMockedEmptyAccount() {
        return Optional.of(BankAccount.builder()
                .id(1L)
                .balance(new BigDecimal(0))
                .build());
    }
}
