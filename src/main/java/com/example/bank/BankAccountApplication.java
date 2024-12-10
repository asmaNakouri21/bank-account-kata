package com.example.bank;

import com.example.bank.dao.BankAccountHistoryRepository;
import com.example.bank.dao.BankAccountRepository;
import com.example.bank.model.BankAccount;
import com.example.bank.model.Deposit;
import com.example.bank.model.Withdraw;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class BankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(BankAccountRepository repository) {
        return (args) -> {
            // Créer un nouveau compte
            BankAccount account = new BankAccount();
            account.setBalance(new BigDecimal(500.0));
            repository.save(account);
        };
    }
}
