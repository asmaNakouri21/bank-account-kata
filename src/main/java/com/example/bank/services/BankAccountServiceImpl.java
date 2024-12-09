package com.example.bank.services;

import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Override
    public void deposit(BankAccount account, BigDecimal amount) throws DepositAcountException {
        Objects.requireNonNull(amount, "Deposit amount cannot be null.");
        if (amount.signum() <= 0) {
            throw new DepositAcountException("Deposit amount must be greater than zero.");
        }
        account.setBalance(account.getBalance().add(amount));
    }
}
