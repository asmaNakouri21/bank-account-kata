package com.example.bank.controllers.controllers;


import com.example.bank.exception.DepositAcountException;
import com.example.bank.model.BankAccount;
import com.example.bank.services.BankAccountService;
import com.example.bank.services.BankAccountServiceImpl;
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
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{id}/depositAccount")
    public ResponseEntity<String> makeDeposit(@PathVariable Long id,
                                              @RequestParam(name = "amount") BigDecimal amount) {
        return ResponseEntity.ok("OK");

    }
}
