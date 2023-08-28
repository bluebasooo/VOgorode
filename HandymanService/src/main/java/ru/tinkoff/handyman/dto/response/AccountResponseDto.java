package ru.tinkoff.handyman.dto.response;

import lombok.*;
import org.apache.tomcat.jni.User;
import ru.tinkoff.handyman.model.PaySystem;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private UserResponseDto owner;
    private String cardNumbers;
    private PaySystem paySystem;
}
