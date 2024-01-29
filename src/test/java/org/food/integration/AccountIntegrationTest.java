package org.food.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.dto.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountIntegrationTest {

    @BeforeEach
    public void init(){

    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("")
    void getAllAccounts() throws Exception {
        MvcResult result = mockMvc.perform(get("/accounts/")
                        .param("id", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        List<AccountDto> accounts = objectMapper.readValue(content, new TypeReference<List<AccountDto>>() {});

        assertNotNull(accounts);
        assertEquals(10, accounts.size());
    }

    @Test
    @DisplayName("")
    void addAccount() throws Exception {

        AccountDto accountDto = new AccountDto();
        accountDto.setName("Новый аккаунт");

        String requestBody = objectMapper.writeValueAsString(accountDto);

        MvcResult result = mockMvc.perform(get("/accounts/")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        AccountDto createdAccountDto = objectMapper.readValue(content, AccountDto.class);

        assertNotNull(createdAccountDto);
        assertEquals("Новый аккаунт", createdAccountDto.getName());
    }

}
