package ru.tinkoff.handyman.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.handyman.dto.request.CreatingAccountDto;
import ru.tinkoff.handyman.dto.response.AccountResponseDto;
import ru.tinkoff.handyman.dto.response.UserResponseDto;
import ru.tinkoff.handyman.exeption.ResourseNotFoundException;
import ru.tinkoff.handyman.model.Account;
import ru.tinkoff.handyman.model.User;
import ru.tinkoff.handyman.repository.AccountRepository;

import javax.validation.Valid;

@Service
@AllArgsConstructor
@Validated
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    /**
     * +++/TO/DO: Возможные ошибки - Валидация не пройдена --> Решено посредством обработки валидации
     * TODO: не прошли констры --
     * @param creatingAccountDt
     * @return
     */
    public AccountResponseDto createAccount(@Valid CreatingAccountDto creatingAccountDt) {
       UserResponseDto owner = userService.getUserById(creatingAccountDt.getUserId());

       Account accountToCreate = mapper.map(creatingAccountDt, Account.class);
       accountToCreate.setUser(
               User.builder()
                       .id(owner.getId())
                       .build()
       );

       Account createdAccount = accountRepository.save(accountToCreate);

       AccountResponseDto responseDto = mapper.map(createdAccount, AccountResponseDto.class);
       responseDto.setOwner(owner);
       return responseDto;
    }

    /**
     * ++TO/DO: Ошибки ---> решено посредством проикдования исключения NotFOund
     * @param userId
     * @return
     */
    public AccountResponseDto getAccountById(Long userId) {
        Account findedAccount = accountRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException("Account with id: [" + userId + "] - not found"));

        UserResponseDto owner = mapper.map(findedAccount.getUser(), UserResponseDto.class);
        AccountResponseDto responseDto = mapper.map(findedAccount, AccountResponseDto.class);
        responseDto.setOwner(owner);
        return responseDto;
    }

    /**
     * TODO: Ошибки
     * TODO: не найден юзер
     * TODO: не прошла валидация
     * @param userId
     * @return
     */
    public AccountResponseDto updateAccountById(Long userId) {
        return null;
    }

    /**
     * TODO: Ошибки
     * +++TO/DO: Не найден юзер -> решено в методе getAccountById
     * @param userId
     * @return
     */
    public AccountResponseDto deleteAccountById(Long userId) {
        AccountResponseDto accountToDelete = getAccountById(userId);
        accountRepository.deleteById(userId);
        return accountToDelete;
    }
}
