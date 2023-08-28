package ru.tinkoff.handyman.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.handyman.dto.request.CreatingAccountDto;
import ru.tinkoff.handyman.dto.response.AccountResponseDto;
import ru.tinkoff.handyman.service.AccountService;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public AccountResponseDto createAccount(@RequestBody CreatingAccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/{userId}")
    public AccountResponseDto getAccountById(@PathVariable Long userId) {
        return accountService.getAccountById(userId);
    }

    @PostMapping("/{userId}")
    public AccountResponseDto updateAccountById(@PathVariable Long userId) {
        return accountService.updateAccountById(userId);
    }

    @DeleteMapping("/{userId}")
    public AccountResponseDto deleteAccountById(@PathVariable Long userId) {
        return accountService.deleteAccountById(userId);
    }
}
