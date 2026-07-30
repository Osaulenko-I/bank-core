package ru.osaulenko.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AddFriendRequestDto {
    @NotNull
    private UUID friendId;
}
