package org.food.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.repository.AccountRepository;
import org.food.dto.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration

@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class AccountIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void init(){

}

    @Test
    @DisplayName("")
    @WithMockUser
    @Sql("classpath:db/data/sql/account/integration/test/accounts-sql-testdata.sql")
    void getAccounts() throws Exception {

        ResultActions result = mockMvc.perform(get("/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        AccountDto response = objectMapper.readValue(contentAsString, AccountDto.class);
        System.out.println(response);
    }

    @Test
    @WithMockUser
    @DisplayName("")
    @Sql("classpath:db/data/sql/account/integration/test/accounts-sql-testdata.sql")
    void addAccount() throws Exception {

        AccountDto accountDto = new AccountDto(1,"Sasha", BigDecimal.valueOf(342), "+4245253562352");



        String requestBody = objectMapper.writeValueAsString(accountDto);

        ResultActions resultActsions = mockMvc.perform(post("/accounts/")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActsions.andReturn();


        String content = result.getResponse().getContentAsString();
        System.out.println(content);
        AccountDto createdAccountDto = objectMapper.readValue(content, AccountDto.class);

        assertNotNull(createdAccountDto);
        assertEquals("Новый аккаунт", createdAccountDto.getName());
    }

}
