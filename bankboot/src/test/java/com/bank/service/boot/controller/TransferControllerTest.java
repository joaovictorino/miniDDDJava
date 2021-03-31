package com.bank.service.boot.controller;

import com.bank.account.model.Account;
import com.bank.account.model.AccountNumber;
import com.bank.account.model.contract.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Repository<Account, AccountNumber> accountRepository;

    @BeforeEach
    public void setup() {
        when(accountRepository.get(argThat(account -> account != null && account.getNumber().equals("123456")))).thenReturn(new Account(new AccountNumber("123456"), 5000.0));
        when(accountRepository.get(argThat(account -> account != null && account.getNumber().equals("654321")))).thenReturn(new Account(new AccountNumber("654321"), 5000.0));
    }

    @Test
    public void transferMoneySuccess() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/transfer/123456/654321/200")).andExpect(status().isOk()).andReturn();
        String receipt = result.getResponse().getContentAsString();
        assertEquals(6, receipt.length());
    }

    @Test
    public void transferMoneyFailureInvalidValue() throws Exception {
        this.mockMvc.perform(post("/transfer/123456/654321/-10")).andExpect(status().isBadRequest());
    }

    @Test
    public void transferMoneyFailureExceedLimit() throws Exception {
        this.mockMvc.perform(post("/transfer/123456/654321/10000")).andExpect(status().isBadRequest());
    }
}