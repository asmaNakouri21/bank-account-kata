package com.example.bank.controller;

import com.example.bank.dtao.OperationDto;
import com.example.bank.exception.HistoryNotFoundException;
import com.example.bank.services.impl.BankAccountHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class BankAccountHistoryContollerTest {

    @InjectMocks
    BankAccountHistoryContoller historyContoller;

    @Mock
    BankAccountHistoryServiceImpl historyService;

    private MockMvc mockMvc;

    private static final Long ACCOUNT_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(historyContoller).build();
    }
    private static final Long VALID_ACCOUNT_ID = 1L;
    private static final Long INVALID_ACCOUNT_ID = 999L;


    @Test
    @DisplayName("Should return 200 OK and a list of operations when account history is available")
    void shouldReturnAccountHistorySuccessfully() throws Exception {
        // Arrange
        when(historyService.getHistoryByAccountId(VALID_ACCOUNT_ID)).thenReturn(getMockHistory());

        // Act & Assert
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/accounts/{id}/history", VALID_ACCOUNT_ID)).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String responseExpected = "[{\"id\":1,\"operationDate\":[2024,1,10,10,15,30],\"amount\":100.0,\"typeOp\":\"DEPOSIT\"},{\"id\":2,\"operationDate\":[2024,1,11,12,20],\"amount\":50.0,\"typeOp\":\"WITHDRAW\"}]";
        assertEquals(responseExpected, response.getContentAsString());

    }

    @Test
    @DisplayName("Should return 404 Not Found when account history is unavailable")
    void shouldReturnNotFoundWhenAccountHistoryIsUnavailable() throws Exception {

        when(historyService.getHistoryByAccountId(INVALID_ACCOUNT_ID))
                .thenThrow(new HistoryNotFoundException(INVALID_ACCOUNT_ID));


        // Act & Assert
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/accounts/{id}/history", INVALID_ACCOUNT_ID)).andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }
    private List<OperationDto> getMockHistory() {
        return List.of(
                new OperationDto(1L, LocalDateTime.of(2024, 1, 10, 10, 15, 30), BigDecimal.valueOf(100.0), "DEPOSIT"),
                new OperationDto(2L, LocalDateTime.of(2024, 1, 11, 12, 20, 0), BigDecimal.valueOf(50.0), "WITHDRAW")
        );
    }

}