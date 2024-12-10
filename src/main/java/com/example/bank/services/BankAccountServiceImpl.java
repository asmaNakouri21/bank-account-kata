package com.example.bank.services;

import com.example.bank.dao.BankAccountRepository;
import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) throws DepositAcountException {
        BankAccount account = getAccountById(accountId);
        Objects.requireNonNull(amount, "Deposit amount cannot be null.");
        if (amount.signum() <= 0) {
            throw new DepositAcountException("Deposit amount must be greater than zero.");
        }
        account.setBalance(account.getBalance().add(amount));
    }

    private BankAccount getAccountById(Long accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
    }
}
