package com.example.bank.services.impl;

import com.example.bank.dao.BankAccountRepository;
import com.example.bank.enums.OperationType;
import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import com.example.bank.model.Operation;
import com.example.bank.services.BankAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void makeDeposit(Long accountId, BigDecimal amount) throws DepositAcountException {
        BankAccount account = getAccountById(accountId);
        validateAmount(amount, account.getBalance(), OperationType.DEPOSIT);
        updateAccountBalance(account, amount);
    }

    @Override
    public BankAccount getAccountById(Long accountId) throws DepositAcountException {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new DepositAcountException("Account not found"));
    }

    @Override
    public void makeWithdraw(Long accountId, BigDecimal amount) throws DepositAcountException {
        BankAccount account = getAccountById(accountId);
        validateAmount(amount, account.getBalance(), OperationType.WITHDRAWAL);
        updateAccountBalance(account, amount.negate());
    }

    private void validateAmount(BigDecimal amount, BigDecimal balance, OperationType operationName) throws DepositAcountException {
        Objects.requireNonNull(amount, operationName + " cannot be null.");
        if (amount.signum() <= 0) {
            throw new DepositAcountException(operationName + " must be greater than zero.");
        }
        if (operationName.equals(OperationType.WITHDRAWAL) &&
                balance.compareTo(amount) < 0) {
            throw new DepositAcountException("Insufficient balance for withdrawal.");
        }
    }

    private void updateAccountBalance(BankAccount account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        bankAccountRepository.save(account);
    }
}
