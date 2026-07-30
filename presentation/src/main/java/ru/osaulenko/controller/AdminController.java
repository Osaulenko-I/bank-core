package ru.osaulenko.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.osaulenko.dto.request.account.GetAccountByIdRequestDto;
import ru.osaulenko.dto.request.account.GetAccountsRequestDto;
import ru.osaulenko.dto.request.account.GetTransactionsRequestDto;
import ru.osaulenko.dto.request.user.*;
import ru.osaulenko.dto.response.AccountResponseDto;
import ru.osaulenko.dto.response.TransactionResponseDto;
import ru.osaulenko.dto.response.UserResponseDto;
import ru.osaulenko.service.AccountService;
import ru.osaulenko.service.ClientService;
import ru.osaulenko.service.security.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final AccountService accountService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createClient(@RequestBody CreateClientRequestDto requestDto) {
        return adminService.createClient(requestDto);
    }

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdmin(@RequestBody CreateAdminRequestDto requestDto) {
        adminService.createAdmin(requestDto);
    }

    @GetMapping("/users/byHair")
    @Operation(summary = "get all users by haircolor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got users by haircolor"),
            @ApiResponse(responseCode = "400", description = "wrong color"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public List<UserResponseDto> getAllUsersByHairColor(
            @ModelAttribute GetUsersFilterByHaircolorRequestDto requestDto) {
        return adminService.getUserByHaircolor(requestDto);
    }

    @GetMapping("/users/byGender")
    @Operation(summary = "get all users by gender")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got users by gender"),
            @ApiResponse(responseCode = "400", description = "wrong gender type"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public List<UserResponseDto> getAllUsersByGender(
            @ModelAttribute GetUserFilterByGenderRequestDto requestDto) {
        return adminService.getUserByGender(requestDto);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "get user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got user by id"),
            @ApiResponse(responseCode = "400", description = "wrong userId type"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public UserResponseDto getUserById(@ModelAttribute GetUserByIdRequestDto requestDto) {
        return adminService.getUserById(requestDto);
    }


    @GetMapping("/accounts")
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountResponseDto> getAccountByUserId(GetAccountsRequestDto requestDto) {
        return accountService.getAllAccountsFilterById(requestDto);
    }

    @GetMapping("/users/getAll")
    public List<UserResponseDto> getUsers() {
        return adminService.getUsers();
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDto getAccountById(GetAccountByIdRequestDto requestDto) {
        return accountService.getAccount(requestDto);
    }

    @GetMapping("/accounts/transactions")
    public List<TransactionResponseDto> getTransactions(GetTransactionsRequestDto requestDto) {
        return accountService.getTransactions(requestDto);
    }
}
