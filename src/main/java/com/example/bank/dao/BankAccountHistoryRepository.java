package com.example.bank.dao;

import com.example.bank.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BankAccountHistoryRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByMyAccount_Id(Long accountId);

}
