package com.example.bank.services;

import com.example.bank.dtao.dto.OperationDto;
import com.example.bank.model.Operation;

import java.util.List;

public interface BankAccountHistoryService {
    public List<OperationDto> getHistoryByAccountId(Long accountId);
}
