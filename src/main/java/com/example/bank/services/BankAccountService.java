package com.example.bank.services;

import com.example.bank.model.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {
    void makeDeposit(Long accountId, BigDecimal amount);

    BankAccount getAccountById(Long accountId);
    void makeWithdraw(Long accountId, BigDecimal amount);
}
