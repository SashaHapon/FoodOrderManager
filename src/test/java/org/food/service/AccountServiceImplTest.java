package org.food.service;

import org.food.api.repository.AccountRepository;
import org.food.dto.AccountDto;
import org.food.model.Account;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    public void getAllAccounts(){

        int id = 1;
        int limit = 10;

        List<Account> mockAccounts = Arrays.asList(new Account(), new Account(), new Account());
        List<AccountDto> mockAccountDtos = Arrays.asList(new AccountDto(), new AccountDto(), new AccountDto());

        when(accountRepository.findAll(id, limit)).thenReturn(mockAccounts);
        Type listType = new TypeToken<List<AccountDto>>() {}.getType();
        when(modelMapper.map(accountRepository.findAll(id, limit), listType)).thenReturn(mockAccountDtos);

        List<AccountDto> accountDtos = accountService.getAllAccounts(id,limit);
        assertEquals(accountDtos, mockAccountDtos);
    }

    @Test
    @DisplayName("add account")
    public void addAccount() {

        AccountDto accountDto = new AccountDto();
        Account mappedAccount = new Account();
        AccountDto expectedAccountDtoOutput = new AccountDto();

        when(modelMapper.map(accountDto, Account.class)).thenReturn(mappedAccount);
        when(modelMapper.map(accountRepository.create(mappedAccount), AccountDto.class)).thenReturn(expectedAccountDtoOutput);

        // Создаем мок для объекта Account, который будет возвращен при вызове accountRepository.create
        AccountDto createdAccountDto = accountService.addAccount(accountDto);

        assertEquals(expectedAccountDtoOutput, createdAccountDto);
    }

    @Test
    @DisplayName("get account")
    public void getAccount() {
        int id = 1;
        AccountDto expectedAccountDtoOutput = new AccountDto();

        when(modelMapper.map(accountRepository.findById(id), AccountDto.class)).thenReturn(expectedAccountDtoOutput);
        AccountDto createdAccountDto = accountService.getAccount(id);

        assertEquals(expectedAccountDtoOutput, createdAccountDto);
    }

    public void deleteAccountById(Integer id) {

        Account account = accountRepository.findById(id);
        accountRepository.delete(account);
    }

    public void update(AccountDto accountDTO) {

        accountRepository.update(modelMapper.map(accountDTO, Account.class));
    }
}
