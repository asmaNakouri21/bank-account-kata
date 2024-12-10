package com.example.bank.controllers.controllers;


import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import com.example.bank.services.BankAccountService;
import com.example.bank.services.impl.BankAccountServiceImpl;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class BankAccountController {

    private final BankAccountService bankService;

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    public BankAccountController(BankAccountServiceImpl bankService) {
        this.bankService = bankService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable @NotNull Long id) {
        try {
            BankAccount account = bankService.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (DepositAcountException e) {
            logger.error("Account not found with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/depositAccount")
    public ResponseEntity<String> makeDeposit(@PathVariable Long id,
                                              @RequestParam(name = "amount") BigDecimal amount) {
        try {
            bankService.makeDeposit(id, amount);
            return ResponseEntity.ok("Deposit successful");
        } catch (DepositAcountException e) {
            logger.error("Deposit failed for account ID: {} with amount: {}", id, amount, e);
            String errorMessage = "Deposit failed: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            logger.error("Unexpected error during deposit for account ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/{id}/withdrawalAccount")
    public ResponseEntity<String> makeWithdrawal(@PathVariable Long id,
                                                 @RequestParam(name = "amount") BigDecimal amount) {
        try {
            bankService.makeWithdraw(id, amount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (DepositAcountException e) {
            logger.error("Withdrawal failed for account ID: {} with amount: {}", id, amount, e);
            String errorMessage = "Withdrawal failed: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            logger.error("Unexpected error during Withdrawal for account ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
