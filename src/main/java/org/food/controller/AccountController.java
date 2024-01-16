package org.food.controller;

import lombok.RequiredArgsConstructor;
import org.food.api.service.AccountService;
import org.food.dto.AccountDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public List<AccountDto> getAllAccounts(@RequestParam(defaultValue = "1", required = false) int id,
                                           @RequestParam(defaultValue = "10",required = false) int limit) {

        return accountService.getAllAccounts(id, limit);
    }

    @PostMapping("/")
    public AccountDto addAccount(@RequestBody AccountDto accountDto) {

        return accountService.addAccount(accountDto);
    }

    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable("id") Integer id) {

        return accountService.getAccount(id);
    }

    @DeleteMapping("/{id}")
    void deleteAccountById(@PathVariable("id") Integer id) {

        accountService.deleteAccountById(id);
    }

    @PutMapping("/{id}")
    void update(@RequestBody AccountDto accountDto) {

        accountService.update(accountDto);
    }
}
