package com.example.bank.service;

import com.example.bank.dao.BankAccountHistoryRepository;
import com.example.bank.dtao.dto.OperationDto;
import com.example.bank.model.Deposit;
import com.example.bank.model.Operation;
import com.example.bank.model.Withdraw;
import com.example.bank.services.impl.BankAccountHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountHistoryServiceTest {
    @InjectMocks
    BankAccountHistoryServiceImpl historyService;

    @Mock
    BankAccountHistoryRepository historyRepository;
    private final static Long accountId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should return operation history for a valid account ID")
    public void testRetrieveHistoryForValidAccount() throws AccountNotFoundException {
        // Arrange
        Long accountId = 1L;
        List<Operation> mockOperations = getOperationList();

        when(historyRepository.findAllByMyAccount_Id(accountId)).thenReturn(mockOperations);

        // Act
        List<OperationDto> operations = historyService.getHistoryByAccountId(accountId);

        // Assert
        assertNotNull(operations, "History should not be null");
        assertEquals(3, operations.size(), "History should contain 3 operations");
    }

    @Test
    @DisplayName("Should throw an exception for a non-existing account ID")
    public void testRetrieveHistoryForInvalidAccount() {
        // Arrange
        Long accountId = 999L;
        when(historyRepository.findAllByMyAccount_Id(accountId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(AccountNotFoundException.class,
                () -> historyService.getHistoryByAccountId(accountId),
                "Account not found");
    }

    @Test
    @DisplayName("Should return an empty list for an account with no operations")
    public void testRetrieveEmptyHistory() throws AccountNotFoundException {
        // Arrange
        Long accountId = 2L;
        when(historyRepository.findAllByMyAccount_Id(accountId)).thenReturn(Collections.emptyList());

        // Assert
        assertThrows(AccountNotFoundException.class,
                () -> historyService.getHistoryByAccountId(accountId),
                "History should be empty for an account with no operations");
    }

    private List<Operation> getOperationList() {
        List<Operation> operationList = Arrays.asList(
                Deposit.builder()
                        .id(2L)
                        .amount(100)
                        .operationDate(new Date())
                        .build(),
                Withdraw.builder()
                        .id(1L)
                        .operationDate(new Date())
                        .amount((50))
                        .build(),
                Deposit.builder()
                        .id(3L)
                        .amount(30)
                        .operationDate(new Date())
                        .build()
        );
        return operationList;
    }

}
