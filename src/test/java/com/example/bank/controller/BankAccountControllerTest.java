package com.example.bank.controller;

import com.example.bank.exception.AccountNotFoundException;
import com.example.bank.exception.InvalidAmountException;
import com.example.bank.model.BankAccount;
import com.example.bank.services.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class BankAccountControllerTest {
    @InjectMocks
    BankAccountController bankAccountController;

    @Mock
    BankAccountServiceImpl bankAccountService;

    private MockMvc mockMvc;

    private static final Long ACCOUNT_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();
    }

    @Test
    @DisplayName("Should return the account and statut OK when the id exists")
    public void testGetAccountById_Success() throws Exception {
        BankAccount mockAccount = new BankAccount(1L,  new BigDecimal("100.00"), new ArrayList<>());
        when(bankAccountService.getAccountById(ACCOUNT_ID)).thenReturn(mockAccount);

        MockHttpServletResponse response = mockMvc.perform(
                get("/account/1")).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"id\":1,\"balance\":100.00,\"operations\":[]}", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return Not Found when the id does not exists")
    public void testGetAccountById_NotFound() throws Exception {
        Long accountId = 999L;
        when(bankAccountService.getAccountById(accountId)).thenThrow(new AccountNotFoundException(accountId));

        MockHttpServletResponse response = mockMvc.perform(
                get("/account/999")).andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
    @Test
    @DisplayName("Should return 200 OK when deposit is successful")
    public void testMakeDeposit_Success() throws Exception {
        BigDecimal amount = new BigDecimal("100.00");
        doNothing().when(bankAccountService).makeDeposit(eq(ACCOUNT_ID), eq(amount));

        MockHttpServletResponse response = mockMvc
                .perform(post("/account/{id}/depositAccount", ACCOUNT_ID)
                        .param("amount", amount.toString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Deposit successful", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return 400 Bad Request when deposit fails with DepositAcountException")
    public void testMakeDeposit_Fail_DepositAcountException() throws Exception {
        BigDecimal amount = new BigDecimal("-50.00"); // Invalid deposit (negative)

        // Mocking the service to throw DepositAcountException
        doThrow(new InvalidAmountException("Deposit amount must be greater than zero")).when(bankAccountService).makeDeposit(eq(ACCOUNT_ID), eq(amount));

        MockHttpServletResponse response = mockMvc
                .perform(post("/account/{id}/depositAccount", ACCOUNT_ID)
                        .param("amount", amount.toString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Deposit failed: Deposit amount must be greater than zero", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return 500 Internal Server Error when an unexpected error occurs")
    public void testMakeDeposit_Fail_InternalServerError() throws Exception {
        BigDecimal amount = new BigDecimal("100.00");
        doThrow(new RuntimeException("Unexpected error")).when(bankAccountService).makeDeposit(eq(ACCOUNT_ID), eq(amount));

        MockHttpServletResponse response = mockMvc
                .perform(post("/account/{id}/depositAccount", ACCOUNT_ID)
                        .param("amount", amount.toString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Internal Server Error", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return 200 OK when Withdrawal is successful")
    public void testMakeWithdrawal_Success() throws Exception {
        BigDecimal amount = new BigDecimal("100.00");

        doNothing().when(bankAccountService).makeWithdraw(eq(ACCOUNT_ID), eq(amount));

        MockHttpServletResponse response = mockMvc
                .perform(post("/account/{id}/withdrawalAccount", ACCOUNT_ID)
                        .param("amount", amount.toString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Withdrawal successful", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return 400 Bad Request when Withdrawal fails with Exception")
    public void testMakeWithdrawal_Fail_WithdrawalAcountException() throws Exception {
        BigDecimal amount = new BigDecimal("-50.00");

        doThrow(new InvalidAmountException("Withdrawal amount must be greater than zero")).when(bankAccountService)
                .makeWithdraw(eq(ACCOUNT_ID), eq(amount));

        MockHttpServletResponse response = mockMvc
                .perform(post("/account/{id}/withdrawalAccount", ACCOUNT_ID)
                        .param("amount", amount.toString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Withdrawal failed: Withdrawal amount must be greater than zero", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return 500 Internal Server Error when an unexpected error occurs")
    public void testMakeWithdrawal_Fail_InternalServerError() throws Exception {
        BigDecimal amount = new BigDecimal("100.00");

        doThrow(new RuntimeException("Unexpected error")).when(bankAccountService).makeWithdraw(eq(ACCOUNT_ID), eq(amount));

        MockHttpServletResponse response = mockMvc
                .perform(post("/account/{id}/withdrawalAccount", ACCOUNT_ID)
                        .param("amount", amount.toString()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Internal Server Error", response.getContentAsString());
    }
}