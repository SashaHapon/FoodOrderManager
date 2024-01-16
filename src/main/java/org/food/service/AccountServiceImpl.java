package org.food.service;

import lombok.RequiredArgsConstructor;
import org.food.api.repository.AccountRepository;
import org.food.api.service.AccountService;
import org.food.dto.AccountDto;
import org.food.model.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;

    private final AccountRepository accountRepository;

    @Override
    public List<AccountDto> getAllAccounts(int id, int limit) {

        Type listType = new TypeToken<List<AccountDto>>() {}.getType();
        return modelMapper.map(accountRepository.findAll(id, limit), listType);
    }

    @Override
    public AccountDto addAccount(AccountDto accountDTO) {

        Account account = modelMapper.map(accountDTO, Account.class);
        return modelMapper.map(accountRepository.create(account), AccountDto.class);
    }

    @Override
    public AccountDto getAccount(Integer id) {

        return modelMapper.map(accountRepository.findById(id), AccountDto.class);
    }

    @Override
    public void deleteAccountById(Integer id) {

        Account account = accountRepository.findById(id);
        accountRepository.delete(account);
    }

    @Override
    public void update(AccountDto accountDTO) {

        accountRepository.update(modelMapper.map(accountDTO, Account.class));
    }
}
