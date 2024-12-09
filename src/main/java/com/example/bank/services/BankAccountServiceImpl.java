package com.example.bank.services;

import com.example.bank.model.BankAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountServiceImpl implements BankAccountService{
    @Override
    public void deposit(BankAccount account, BigDecimal amount) {

    }
}
