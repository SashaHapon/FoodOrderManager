package org.food.service;

import jakarta.persistence.EntityNotFoundException;
import org.food.api.repository.AccountRepository;
import org.food.dto.AccountDto;
import org.food.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(AccountServiceImplTest.class);
    }

    @Test
    @DisplayName("return all AccountDto")
    public void testGetAllAccounts(){

        int id = 1;
        int limit = 10;

        List<Account> mockAccounts = Arrays.asList(new Account(), new Account(), new Account());
        List<AccountDto> mockAccountDtos = Arrays.asList(new AccountDto(), new AccountDto(), new AccountDto());

        when(accountRepository.findAll(id, limit)).thenReturn(mockAccounts);
        Type listType = new TypeToken<List<AccountDto>>() {}.getType();
        when(modelMapper.map(accountRepository.findAll(id, limit), listType)).thenReturn(mockAccountDtos);

        List<AccountDto> accountDtos = accountService.getAllAccounts(id,limit);

        assertThat(accountDtos).isEqualTo(mockAccountDtos);
    }

  //  @Test
  //  @DisplayName("return all AccountDto")
    public void testGetAllAccount_throwException(){
        int id = 1;
        int limit = 10;

        List<Account> mockAccounts = Arrays.asList(new Account(), new Account(), new Account());

        when(accountRepository.findAll(id, limit)).thenReturn(mockAccounts);
        Type listType = new TypeToken<List<AccountDto>>() {}.getType();
        when(modelMapper.map(accountRepository.findAll(id, limit), listType)).thenThrow();
        // ???
        accountService.getAllAccounts(id,limit);

     //   assertThatExceptionOfType().isThrownBy(accountService.getAllAccounts(id,limit));
    }

    @Test
    @DisplayName("addAccount() creating account and return Account")
    public void testAddAccount_ReturnAccount() {
        AccountDto accountDto = new AccountDto();
        Account mappedAccount = new Account();
        Account returnAccount = new Account();
        AccountDto expectedAccountDtoOutput = new AccountDto();

        when(modelMapper.map(accountDto, Account.class)).thenReturn(mappedAccount);
        when(accountRepository.create(mappedAccount)).thenReturn(returnAccount);
        when(modelMapper.map(returnAccount, AccountDto.class)).thenReturn(expectedAccountDtoOutput);

        AccountDto createdAccountDto = accountService.addAccount(accountDto);

        assertThat(expectedAccountDtoOutput).isEqualTo(createdAccountDto);
    }

    @Test
    @DisplayName("addAccount() try to create account and throw exception")
    public void testAddAccount_throwException() {
        AccountDto accountDto = new AccountDto();
        Account mappedAccount = new Account();

        when(modelMapper.map(accountDto, Account.class)).thenReturn(mappedAccount);
        when(accountRepository.create(mappedAccount)).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> accountService.addAccount(accountDto));
    }

    @Test
    @DisplayName("getAccount() getting account and return account ")
    public void testGetAccount_returnAccount() {
        int id = 1;
        AccountDto expectedAccountDtoOutput = new AccountDto();
        Account testAccount = new Account();

        when(accountRepository.findById(id)).thenReturn(testAccount);
        when(modelMapper.map(testAccount, AccountDto.class)).thenReturn(expectedAccountDtoOutput);

        AccountDto createdAccountDto = accountService.getAccount(id);

        assertThat(expectedAccountDtoOutput).isEqualTo(createdAccountDto);
    }

    @Test
    @DisplayName("getAccount() try to get account and throw exception")
    public void testGetAccount_throwException() {
        int id = 1;

        when(accountRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> accountService.getAccount(id));
    }

    @Test
    public void testDeleteAccountById_returnAccount() {

        Account testAccount = new Account();
        testAccount.setId(1);
        testAccount.setName("Dave");

        when(accountRepository.findById(1)).thenReturn(testAccount);

        accountService.deleteAccountById(1);

        verify(accountRepository, times(1)).delete(testAccount);
    }

    @Test
    public void testDeleteAccountById_andThrowException() {
        Integer id = 1;

        when(accountRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> accountService.deleteAccountById(id));
    }

    @Test
    public void update() {

        AccountDto accountDto = new AccountDto();
        accountDto.setId(1);
        accountDto.setName("Dave");

        Account testAccount = new Account();
        testAccount.setId(1);
        testAccount.setName("Dave");

        when(modelMapper.map(accountDto, Account.class)).thenReturn(testAccount);
        when(accountRepository.update(testAccount)).thenReturn(testAccount);

        accountService.update(accountDto);

        verify(accountRepository, times(1)).update(testAccount);
    }

    @Test
    public void update_throwException() {

        AccountDto accountDto = new AccountDto();
        Account testAccount = new Account();

        when(modelMapper.map(accountDto, Account.class)).thenReturn(testAccount);
        when(accountRepository.update(testAccount)).thenThrow(NullPointerException.class);

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> accountService.update(accountDto));
    }
}
