package com.example.bank.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long accountId) {
        super("Account not found " + accountId);
    }
}
