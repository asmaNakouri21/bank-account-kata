package com.example.bank.exception;

public class HistoryNotFoundException extends RuntimeException {
    public HistoryNotFoundException(Long accountId) {
        super("History not found " + accountId);
    }
}
