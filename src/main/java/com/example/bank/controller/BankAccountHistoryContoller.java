package com.example.bank.controller;

import com.example.bank.dtao.OperationDto;
import com.example.bank.exception.HistoryNotFoundException;
import com.example.bank.services.BankAccountHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/accounts")
public class BankAccountHistoryContoller {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountHistoryContoller.class);

    @Autowired
    private  BankAccountHistoryService historyService;

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getAccountHistory(@PathVariable Long id) {
        try {
            // Call service to fetch account history
            List<OperationDto> history = historyService.getHistoryByAccountId(id);

            // Return successful response with history data
            return ResponseEntity.ok(history);
        } catch (HistoryNotFoundException e) {
            // Log and return NOT_FOUND error for account issues
            logger.error("Error fetching account history for ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Log and return INTERNAL_SERVER_ERROR for unexpected issues
            logger.error("Unexpected error occurred while fetching account history for ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }
}
