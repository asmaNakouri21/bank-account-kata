package com.example.bank.services.impl;

import com.example.bank.dao.BankAccountHistoryRepository;
import com.example.bank.dtao.OperationDto;
import com.example.bank.exception.HistoryNotFoundException;
import com.example.bank.model.Deposit;
import com.example.bank.model.Operation;
import com.example.bank.model.Withdraw;
import com.example.bank.services.BankAccountHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BankAccountHistoryServiceImpl implements BankAccountHistoryService {
    @Autowired
    private BankAccountHistoryRepository historyRepository;

    @Override
    public List<OperationDto> getHistoryByAccountId(Long accountId){
        var operations = historyRepository.findAllByMyAccount_Id(accountId);
        if (operations == null || operations.isEmpty()) {
            throw new HistoryNotFoundException(accountId);

        }
        return operations.stream()
                .map(operation -> OperationDto.builder()
                        .id(operation.getId())
                        .operationDate(operation.getOperationDate())
                        .amount(operation.getAmount())
                        .typeOp(getOperationType(operation))
                        .build())
                .toList();
    }

    private String getOperationType(Operation operation) {
        return switch (operation) {
            case Deposit d -> "DEPOSIT";
            case Withdraw w -> "WITHDRAW";
            default -> throw new IllegalStateException("Unknown operation type: " + operation.getClass().getName());
        };
    }
}
