package org.food.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.food.api.service.AccountService;
import org.food.dto.AccountDto;
import org.food.exception.classes.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// todo enable filters
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@Rollback
public class AccountIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    AccountService accountService;

    private String getJsonAsString(String name){
        try {
            Path path = Paths.get("src/test/resources/db/data/account/integration/test/" + name);
            return Files.readString(path);
        }catch (IOException e){
            throw new RuntimeException(e.getCause());
        }
    }


    @Test
    @WithMockUser
    @Sql("classpath:db/data/testdata.sql")
    @DisplayName("Return account from database with id=1")
    void should_return_Account_with_id1() throws Exception {

        mockMvc.perform(get("/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("getAccount_expectedAccountDto.json")));
    }

    @Test
    @WithMockUser
    @Sql("classpath:db/data/testdata.sql")
    @DisplayName("Throw NotFoundException when try to get account with id=0)")

    void should_throw_notFoundException_when_getAccount() throws Exception {

        ResultActions result = mockMvc.perform(get("/accounts/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Add new account to database")
    void should_addAccount_toDb() throws Exception {

        mockMvc.perform(post("/accounts/")
                        .content(getJsonAsString("addAccount_inputAccountDto.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("addAccount_expectedAccountDto.json")));
    }

    @Test
    @WithMockUser
    @DisplayName("Throw BadRequestException because input json is incorrect")
    void should_throwException_when_tryToAddAccount() throws Exception {


        String requestBody = objectMapper.writeValueAsString("incorrect message");

        mockMvc.perform(post("/accounts/")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("Return all accounts from database")
    @Sql("classpath:db/data/testdata.sql")
    public void should_return_allAccounts() throws Exception {

        mockMvc.perform(get("/accounts/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonAsString("getAll_expectedListAccountDto.json")));
    }


    @Test
    @WithMockUser
    @DisplayName("Delete account with id=3")
    @Sql("classpath:db/data/testdata.sql")
    public void should_delete_account_withId_3() throws Exception {
        mockMvc.perform(delete("/accounts/3"))
                .andDo(print())
                .andExpect(status().isOk());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> accountService.getAccount(3));
    }


    @Test
    @WithMockUser
    @DisplayName("Throw NotFoundException when try to delete account with incorrect id")
    public void deleteNonExistingAccountById() throws Exception {
        mockMvc.perform(delete("/accounts/404"))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> accountService.deleteAccountById(404));
    }

    @Test
    @WithMockUser
    @DisplayName("Update account with id=4")
    @Sql("classpath:db/data/testdata.sql")
    void should_update_account_with_id_4() throws Exception {

        mockMvc.perform(put("/accounts/4")
                        .content(getJsonAsString("updateAccount_inputAccountDto.json"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        AccountDto inputAccountDto = objectMapper.readValue(getJsonAsString("updateAccount_inputAccountDto.json"), AccountDto.class);
        AccountDto accountDto = accountService.getAccount(4);
        assertEquals(accountDto, inputAccountDto);
    }
}
