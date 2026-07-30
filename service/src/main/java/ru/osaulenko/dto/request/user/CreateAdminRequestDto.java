package ru.osaulenko.dto.request.user;


import lombok.Data;

@Data
public class CreateAdminRequestDto {
    String username;
    String password;
}
