package org.food.service;

import jakarta.persistence.EntityNotFoundException;
import org.food.api.repository.AccountRepository;
import org.food.dto.AccountDto;
import org.food.model.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTests {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("All accounts must be returned")
    public void should_returnAllAccounts_when_tryToGetAllAccounts() {

        int id = 1;
        int limit = 10;

        List<Account> mockAccounts = Arrays.asList(new Account(), new Account(), new Account());
        List<AccountDto> mockAccountDtos = Arrays.asList(new AccountDto(), new AccountDto(), new AccountDto());

        when(accountRepository.findAll(id, limit)).thenReturn(mockAccounts);
        Type listType = new TypeToken<List<AccountDto>>() {
        }.getType();
        when(modelMapper.map(accountRepository.findAll(id, limit), listType)).thenReturn(mockAccountDtos);

        List<AccountDto> accountDtos = accountService.getAllAccounts(id, limit);

        assertThat(accountDtos).isEqualTo(mockAccountDtos);
    }

    @Test
    @DisplayName("Throw exception when try to get all accounts")
    public void should_throwException_when_tryToGetAllAccounts() {
        int id = 1;
        int limit = 10;

        List<Account> mockAccounts = Arrays.asList(new Account(), new Account(), new Account());

        when(accountRepository.findAll(id, limit)).thenReturn(mockAccounts);
        Type listType = new TypeToken<List<AccountDto>>() {
        }.getType();
        when(modelMapper.map(accountRepository.findAll(id, limit), listType)).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> accountService.getAllAccounts(id, limit));
    }

    @Test
    @DisplayName("Return account after adding account")
    public void should_returnAccount_when_tryToAddAccount() {
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
    @DisplayName("Throw exception after adding account")
    public void should_throwException_when_tryToAddAccount() {
        AccountDto accountDto = new AccountDto();
        Account mappedAccount = new Account();

        when(modelMapper.map(accountDto, Account.class)).thenReturn(mappedAccount);
        when(accountRepository.create(mappedAccount)).thenThrow(IllegalArgumentException.class);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> accountService.addAccount(accountDto));
    }

    @Test
    @DisplayName("Return account when try to get account by id")
    public void should_returnAccount_whenTryToGetAccount() {
        int id = 1;
        AccountDto expectedAccountDtoOutput = new AccountDto();
        Account testAccount = new Account();

        when(accountRepository.findById(id)).thenReturn(testAccount);
        when(modelMapper.map(testAccount, AccountDto.class)).thenReturn(expectedAccountDtoOutput);

        AccountDto createdAccountDto = accountService.getAccount(id);

        assertThat(expectedAccountDtoOutput).isEqualTo(createdAccountDto);
    }

    @Test
    @DisplayName("Throw exception when try to get account by id")
    public void should_throwException_when_tryToGetAccount() {
        int id = 1;

        when(accountRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> accountService.getAccount(id));
    }

    @Test
    @DisplayName("Check that method deleteAccountById() calls one time")
    public void should_check_callsMethodOneTime_when_tryToDeleteAccountById() {

        Account testAccount = new Account();
        testAccount.setId(1);
        testAccount.setName("Dave");

        when(accountRepository.findById(1)).thenReturn(testAccount);

        accountService.deleteAccountById(1);

        verify(accountRepository, times(1)).delete(testAccount);
    }

    @Test
    @DisplayName("Throw exception when try to delete account")
    public void should_throwException_when_tryToDeleteAccount() {
        int id = 1;

        when(accountRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> accountService.deleteAccountById(id));
    }

    @Test
    @DisplayName("Return account after update")
    public void should_returnAccount_when_tryToUpdateAccount() {

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
    @DisplayName("Throw exception when try to update account")
    public void should_throwException_when_tryToUpdateAccount() {

        AccountDto accountDto = new AccountDto();
        Account testAccount = new Account();

        when(modelMapper.map(accountDto, Account.class)).thenReturn(testAccount);
        when(accountRepository.update(testAccount)).thenThrow(NullPointerException.class);

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> accountService.update(accountDto));
    }
}
