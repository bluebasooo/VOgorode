package ru.tinkoff.handyman.dto.request;


import lombok.*;
import ru.tinkoff.handyman.model.PaySystem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class CreatingAccountDto {

    @NotNull(message = "Users id is required")
    private final Long userId;

    @NotNull(message = "Card numbers is required")
    @Size(min = 16, max = 16, message = "Card numbers size is 16")
    private final String cardNubmers;

    @NotNull(message = "Pay system is required")
    private PaySystem paySystem;
}
