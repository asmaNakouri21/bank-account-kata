package com.example.bank.services;

import com.example.bank.dtao.OperationDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface BankAccountHistoryService {
    public List<OperationDto> getHistoryByAccountId(Long accountId);
}
