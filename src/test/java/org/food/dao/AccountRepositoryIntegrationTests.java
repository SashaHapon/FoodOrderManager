package org.food.dao;

import org.food.api.repository.AccountRepository;
import org.food.model.Account;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@Rollback
public class AccountRepositoryIntegrationTests {
    @Autowired
    private AccountRepository accountRepository;
    private static final String TEST_DATA_FILE_PREFIX = "classpath:data/org/food/dao/AccountRepositoryIntegrationTests";
    private int id = 1;
    private int limit = 10;
    private Account account1;
    private Account account2;
    private Account account3;
    private Account account4;
    private Account account5;
    private Account newAccount4;
    private Account newAccount;
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    public void init() {
        account1 = new Account(1, "Test Account 1", new BigDecimal("100.01"), "1234567890");
        account2 = new Account(2, "Test Account 2", new BigDecimal("100.01"), "1234567890");
        account3 = new Account(3, "Test Account 3", new BigDecimal("100.01"), "1234567890");
        account4 = new Account(4, "Test Account 4", new BigDecimal("100.01"), "1234567890");
        account5 = new Account(5, "Test Account 5", new BigDecimal("100.01"), "1234567890");
        newAccount4 = new Account(4, "Sasha", new BigDecimal("100.01"), "1234567890");
        newAccount = new Account("Sasha", new BigDecimal("102.01"), "+375448954612");
    }

    @Test
    @DisplayName("New account must be created")
    public void should_createNewAccount() {
        Account account = accountRepository.create(newAccount);
        Account returnedAccount = accountRepository.findById(6);
        assertThat(account).isEqualTo(returnedAccount);
    }

    @Test
    @DisplayName("Account with id=1 must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_findAccountById/insert_account_with_Id_1.sql")
    public void should_findAccountById() {
        Account account = accountRepository.findById(id);
        assertThat(account.getId()).isEqualTo(account1.getId());
        assertThat(account.getName()).isEqualTo(account1.getName());
    }

    @Test
    @DisplayName("Account with id=4 must be updated")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_updateAccount/insert_account_with_id_4.sql")
    public void should_updateAccount() {
        accountRepository.update(newAccount4);
        Account account = accountRepository.findById(4);
        assertThat(account.getId()).isEqualTo(newAccount4.getId());
        assertThat(account.getName()).isEqualTo(newAccount4.getName());
    }

    @Test
    @DisplayName("Account with id=3 must be deleted")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_deleteAccount/insert_account_with_Id_3.sql")
    public void should_deleteAccount() {
        accountRepository.delete(account3);
        Account account = accountRepository.findById(3);
        assertThat(account).isEqualTo(null);
    }

    @Test
    @DisplayName("All accounts must be returned")
    @Sql(TEST_DATA_FILE_PREFIX + "/should_findAllAccounts/insert_five_accounts.sql")
    public void should_findAllAccounts() {
        List<Account> accounts = accountRepository.findAll(id, limit);

        assertThat(accounts.get(0).getId()).isEqualTo(account1.getId());
        assertThat(accounts.get(0).getName()).isEqualTo(account1.getName());

        assertThat(accounts.get(1).getId()).isEqualTo(account2.getId());
        assertThat(accounts.get(1).getName()).isEqualTo(account2.getName());

        assertThat(accounts.get(2).getId()).isEqualTo(account3.getId());
        assertThat(accounts.get(2).getName()).isEqualTo(account3.getName());

        assertThat(accounts.get(3).getId()).isEqualTo(account4.getId());
        assertThat(accounts.get(3).getName()).isEqualTo(account4.getName());

        assertThat(accounts.get(4).getId()).isEqualTo(account5.getId());
        assertThat(accounts.get(4).getName()).isEqualTo(account5.getName());
    }

}
