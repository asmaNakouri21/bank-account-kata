package com.example.bank.services.impl;

import com.example.bank.dtao.dto.OperationDto;
import com.example.bank.model.Operation;
import com.example.bank.services.BankAccountHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class BankAccountHistoryServiceImpl implements BankAccountHistoryService {

    @Override
    public List<OperationDto> getHistoryByAccountId(Long accountId) {
        return null;
    }

}
