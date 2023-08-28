package ru.tinkoff.handyman.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.handyman.dto.request.CreatingUserDto;
import ru.tinkoff.handyman.dto.response.UserResponseDto;
import ru.tinkoff.handyman.exeption.ResourseNotFoundException;
import ru.tinkoff.handyman.model.User;
import ru.tinkoff.handyman.repository.UserRepository;

import javax.validation.Valid;

@Service
@AllArgsConstructor
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    /**
     * TODO: Потенциальные ошибки:
     * TODO: Юзер уже существует --- пока нет так как нет требований на констрейты
     * +++TODO: DTO не прошло валидацию --> решено валидационным хендлером
     * @param userDto
     * @return
     */
    public UserResponseDto createUser(@Valid CreatingUserDto userDto) {
        User userToSave = mapper.map(userDto, User.class);
        User savedUser = userRepository.save(userToSave);

        return mapper.map(savedUser, UserResponseDto.class);
    }

    /**
     * +++TODO: Потенциальные ошибки: Юзер не найден --> решено выкидыванием исключенгия
     * @param userId
     * @return
     */
    public UserResponseDto getUserById(Long userId) {
        User findedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourseNotFoundException("User with id: [" + userId + "] - not found"));

        return mapper.map(findedUser, UserResponseDto.class);
    }

    /**
     * TODO: Потенциальные ошибки: юзер не найден
     * TODO: DTO не прошло валидацию
     * @param userId
     * @return
     */
    public UserResponseDto updateUserById(Long userId) {
        return null;
    }

    /**
     * +++TODO: Потенциальные ошибки: Юзер не найден --> решено в методе getUserById
     * @param userId
     * @return
     */
    public UserResponseDto deleteUserById(Long userId) {
        UserResponseDto userToDelete = getUserById(userId);
        userRepository.deleteById(userId);
        return userToDelete;
    }
}
