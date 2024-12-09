package com.example.bank.services;

import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {
    void deposit(BankAccount account, BigDecimal amount) throws DepositAcountException;
}
