package ru.osaulenko.dto.request.user;

import lombok.Data;

@Data
public class CreateClientRequestDto {
    String username;
    String name;
    boolean male;
    String password;
    String haircolor;
}
