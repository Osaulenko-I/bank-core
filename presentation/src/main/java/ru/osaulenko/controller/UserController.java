package ru.osaulenko.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.osaulenko.dto.request.user.*;
import ru.osaulenko.dto.response.UserResponseDto;
import ru.osaulenko.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User controller")
public class UserController {
    private final ClientService clientService;

    @GetMapping("/get/info")
    @Operation(summary = "get info about profile")
    public UserResponseDto getInfo() {
        return clientService.getProfile();
    }

    @PatchMapping("/add/friend")
    @Operation(summary = "add new friend")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "friend added"),
            @ApiResponse(responseCode = "400", description = "wrong userId type"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public UserResponseDto addFriend(@RequestBody AddFriendRequestDto requestDto) {
        return clientService.addFriend(requestDto);
    }

    @DeleteMapping("/remove/friend")
    @Operation(summary = "remove friend")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "friend removed"),
            @ApiResponse(responseCode = "400", description = "wrong userId type"),
            @ApiResponse(responseCode = "404", description = "user not found"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public UserResponseDto removeFriend(@RequestBody DeleteFriendRequestDto requestDto) {
        return clientService.deleteFriend(requestDto);
    }

    @GetMapping("/get/friend")
    @Operation(summary = "get friends")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got friends"),
            @ApiResponse(responseCode = "400", description = "wrong userId"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public List<UserResponseDto> getFriends() {
        return clientService.getFriends();
    }
}
