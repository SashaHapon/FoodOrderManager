package org.food.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.food.api.service.AccountService;
import org.food.dto.AccountDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AccountIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    private EntityManager entityManager;

    @Sql("classpath:db/data/sql/account/integration/test/accounts-sql-testdata.sql")
    @Test
    @DisplayName("Return account from database with id=1")
    @WithMockUser
    void should_return_Account_with_id1() throws Exception {

        ResultActions result = mockMvc.perform(get("/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        AccountDto response = objectMapper.readValue(contentAsString, AccountDto.class);
    }

    @Test
    @DisplayName("Throw NotFoundException when try to get account with id=0)")
    @WithMockUser
    void should_throw_notFoundException_when_getAccount() throws Exception {

        ResultActions result = mockMvc.perform(get("/accounts/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Add new account to database")
    void should_addAccount_toDb() throws Exception {

        AccountDto accountDto = new AccountDto(null, "Sasha", BigDecimal.valueOf(342), "+4245253562352");


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
        assertEquals("Sasha", createdAccountDto.getName());
    }

    @Test
    @WithMockUser
    @DisplayName("Throw BadRequestException because input json is incorrect")
    void should_throwException_when_tryToAddAccount() throws Exception {


        String requestBody = objectMapper.writeValueAsString("incorrect message");

        ResultActions result = mockMvc.perform(post("/accounts/")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("Return all accounts from database")
    @Sql("classpath:db/data/sql/account/integration/test/accounts-sql-testdata.sql")
    public void should_return_allAccounts() throws Exception {

        AccountDto firstAccountDto = new AccountDto(1, "Test Account 1", new BigDecimal(100.01).setScale(2, RoundingMode.HALF_UP), "1234567890");
        AccountDto secondAccountDto = new AccountDto(2, "Test Account 2", new BigDecimal(100.01).setScale(2, RoundingMode.HALF_UP), "1234567890");

        ResultActions result = mockMvc.perform(get("/accounts/"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = result.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<AccountDto> response = objectMapper.readValue(contentAsString, new TypeReference<List<AccountDto>>() {
        });

        assertTrue(response.contains(firstAccountDto));
        assertTrue(response.contains(secondAccountDto));
    }


    @Test
    @WithMockUser
    @DisplayName("Delete account with id=3")
    @Sql("classpath:db/data/sql/account/integration/test/accounts-sql-testdata.sql")
    public void should_delete_account_withId_3() throws Exception {
        ResultActions result = mockMvc.perform(delete("/accounts/3"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> accountService.getAccount(3));
    }


    @Test
    @WithMockUser
    @DisplayName("Throw NotFoundException when try to delete account with incorrect id")
    public void deleteNonExistingAccountById() throws Exception {
        mockMvc.perform(delete("/accounts/404"))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> accountService.deleteAccountById(404));
    }

    @Test
    @WithMockUser
    @DisplayName("Update account with id=4")
    @Sql("classpath:db/data/sql/account/integration/test/accounts-sql-testdata.sql")
    void should_update_account_with_id_4() throws Exception {
        AccountDto firstAccountDto = new AccountDto(4, "Test Account 4", new BigDecimal(100.01).setScale(2, RoundingMode.HALF_UP), "0000456");
        String requestBody = objectMapper.writeValueAsString(firstAccountDto);
        ResultActions result = mockMvc.perform(put("/accounts/4")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        AccountDto accountDto = accountService.getAccount(4);
        assertEquals(accountDto, firstAccountDto);
    }
}
