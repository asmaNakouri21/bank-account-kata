package com.example.bank.services.impl;

import com.example.bank.dao.BankAccountRepository;
import com.example.bank.enums.OperationType;
import com.example.bank.exception.AccountNotFoundException;
import com.example.bank.exception.InvalidAmountException;
import com.example.bank.model.BankAccount;
import com.example.bank.services.BankAccountService;
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
    public void makeDeposit(Long accountId, BigDecimal amount) throws InvalidAmountException {
        BankAccount account = getAccountById(accountId);
        validateAmount(amount, account.getBalance(), OperationType.DEPOSIT);
        updateAccountBalance(account, amount);
    }

    @Override
    public BankAccount getAccountById(Long accountId) throws AccountNotFoundException {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    @Override
    public void makeWithdraw(Long accountId, BigDecimal amount) {
        BankAccount account = getAccountById(accountId);
        validateAmount(amount, account.getBalance(), OperationType.WITHDRAWAL);
        updateAccountBalance(account, amount.negate());
    }

    private void validateAmount(BigDecimal amount, BigDecimal balance, OperationType operationName) {
        Objects.requireNonNull(amount, operationName + " cannot be null.");
        if (amount.signum() <= 0) {
            throw new InvalidAmountException(operationName + " must be greater than zero.");
        }
        if (operationName.equals(OperationType.WITHDRAWAL) &&
                balance.compareTo(amount) < 0) {
            throw new InvalidAmountException("Insufficient balance for withdrawal.");
        }
    }

    private void updateAccountBalance(BankAccount account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        bankAccountRepository.save(account);
    }
}
