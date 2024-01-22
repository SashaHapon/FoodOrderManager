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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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
    public void addAccount() {

        AccountDto accountDto = new AccountDto("","","","");

        // Создаем мок для объекта Account, который будет возвращен при вызове modelMapper.map
        Account mappedAccount = new Account();
        when(modelMapper.map(accountDTO, Account.class)).thenReturn(mappedAccount);

        // Создаем мок для объекта AccountDto, который будет возвращен при вызове modelMapper.map после создания учетной записи
        AccountDto expectedAccountDtoOutput = new AccountDto(/*...*/);
        when(modelMapper.map(mappedAccount, AccountDto.class)).thenReturn(expectedAccountDtoOutput);

        // Создаем мок для объекта Account, который будет возвращен при вызове accountRepository.create
        Account createdAccount = new Account();
        lenient().when(accountRepository.create(mappedAccount)).thenReturn(createdAccount);

        // Act
        System.out.println(accountService.addAccount(accountDtoInput));

        // Assert
        assertEquals(expectedAccountDtoOutput, accountDtoInput);

        // Проверяем, что методы были вызваны с правильными аргументами
//        verify(modelMapper, times(1)).map(accountDtoInput, Account.class);
//        verify(accountRepository, times(1)).create(mappedAccount);
//        verify(modelMapper, times(1)).map(createdAccount, AccountDto.class);
    }

    public AccountDto getAccount(Integer id) {

        return modelMapper.map(accountRepository.findById(id), AccountDto.class);
    }

    public void deleteAccountById(Integer id) {

        Account account = accountRepository.findById(id);
        accountRepository.delete(account);
    }

    public void update(AccountDto accountDTO) {

        accountRepository.update(modelMapper.map(accountDTO, Account.class));
    }
}
