package org.food.dao;

import org.food.api.repository.AccountRepository;
import org.food.model.Account;
import org.food.testconfig.ContainerConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
@Testcontainers
public class AccountRepositoryIntegrationTests extends ContainerConfiguration {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JdbcDatabaseContainer<?> databaseContainer;

    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/dao/AccountRepositoryIntegrationTests";

    @Test
    @DisplayName("New account must be created")
    public void should_createNewAccount() {
        Account account = accountRepository.create(new Account(null, "Sasha", new BigDecimal("102.01"), "+375448954612"));
        Account returnedAccount = accountRepository.findById(account.getId());
        
        assertThat(account).isEqualTo(returnedAccount);
    }

    @Test
    @DisplayName("Account with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_findAccountById/insert_account_with_Id_1.sql")
    public void should_findAccountById() {
        Account account = accountRepository.findById(1);

        assertThat(account.getId()).isEqualTo(1);
        assertThat(account.getName()).isEqualTo("Test Account 1");
        assertThat(account.getMoney()).isEqualTo(new BigDecimal("100.01"));
        assertThat(account.getPhoneNumber()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Account with id=4 must be updated")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_updateAccount/insert_account_with_id_4.sql")
    public void should_updateAccount() {
        Account newAccount4 = new Account(4, "Sasha", new BigDecimal("100.01"), "1234567890");

        accountRepository.update(newAccount4);
        Account account = accountRepository.findById(4);

        assertThat(account.getId()).isEqualTo(newAccount4.getId());
        assertThat(account.getName()).isEqualTo(newAccount4.getName());
        assertThat(account.getMoney()).isEqualTo(newAccount4.getMoney());
        assertThat(account.getPhoneNumber()).isEqualTo(newAccount4.getPhoneNumber());
    }

    @Test
    @DisplayName("Account with id=3 must be deleted")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_deleteAccount/insert_account_with_Id_3.sql")
    public void should_deleteAccount() {
        Account account3 = accountRepository.findById(3);
        accountRepository.delete(account3);
        Account account = accountRepository.findById(3);

        assertThat(account).isEqualTo(null);
    }

    @Test
    @DisplayName("All accounts must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_findAllAccounts/insert_five_accounts.sql")
    public void should_findAllAccounts() {
        Account account1 = new Account(1, "Test Account 1", new BigDecimal("100.01"), "1234567890");
        Account account2 = new Account(2, "Test Account 2", new BigDecimal("100.01"), "1234567890");
        Account account3 = new Account(3, "Test Account 3", new BigDecimal("100.01"), "1234567890");
        Account account4 = new Account(4, "Test Account 4", new BigDecimal("100.01"), "1234567890");
        Account account5 = new Account(5, "Test Account 5", new BigDecimal("342.00"), "+4245253562352");
        List<Account> accountList = new ArrayList<>(List.of(account1, account2, account3, account4, account5));

        List<Account> accounts = accountRepository.findAll(1, 10);

        assertThat(accounts).isEqualTo(accountList);
    }

}
