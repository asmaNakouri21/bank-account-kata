package com.example.bank.dtao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDto {
    private Long id;

    private LocalDateTime operationDate;

    private BigDecimal amount;

    private String typeOp;
}
