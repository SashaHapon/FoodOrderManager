package org.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.food.api.service.AccountService;
import org.food.dto.AccountDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name="AccountController", description=" Позволяет контролировать аккаунты в бд")
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Получение всех аккаунтов",
            description = "Позволяет получить аккаунты"
    )
    @GetMapping("/")
    public List<AccountDto> getAllAccounts(@RequestParam(defaultValue = "1", required = false) int id,
                                           @RequestParam(defaultValue = "10",required = false) int limit) {

        return accountService.getAllAccounts(id, limit);
    }

    @Operation(
            summary = "Добавление аккаунта",
            description = "Позволяет добавить аккаунт в базу данных"
    )
    @PostMapping("/")
    public AccountDto addAccount(@RequestBody AccountDto accountDto) {
        return accountService.addAccount(accountDto);
    }

    @Operation(
            summary = "Получение аккаунта",
            description = "Позволяет получить аккаунт по id из базы данных"
    )
    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable("id") Integer id) {

        return accountService.getAccount(id);
    }

    @Operation(
            summary = "Удаление аккаунта",
            description = "Позволяет удалить аккаунт по id из базы данных"
    )
    @DeleteMapping("/{id}")
    void deleteAccountById(@PathVariable("id") Integer id) {

        accountService.deleteAccountById(id);
    }

    @Operation(
            summary = "Обновление аккаунта",
            description = "Позволяет обновить данные аккаунта"
    )
    @PutMapping("/{id}")
    void update(@RequestBody AccountDto accountDto) {

        accountService.update(accountDto);
    }
}
