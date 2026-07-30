package ru.osaulenko.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.osaulenko.dto.request.account.*;
import ru.osaulenko.dto.response.AccountResponseDto;
import ru.osaulenko.dto.response.TransactionResponseDto;
import ru.osaulenko.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account controller")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    @Operation(summary = "create account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "account created"),
            @ApiResponse(responseCode = "400", description = "wrong input"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public AccountResponseDto createAccount(@RequestBody CreateAccountRequestDto requestDto) {
        return accountService.create(requestDto);
    }

    @PatchMapping("/operation/debiting")
    @Operation(summary = "debiting account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success debiting"),
            @ApiResponse(responseCode = "400", description = "wrong accountId type"),
            @ApiResponse(responseCode = "404", description = "accountId not found"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public AccountResponseDto debiting(@RequestBody DebitingRequestDto requestDto) {
        return accountService.debiting(requestDto);
    }

    @PatchMapping("/operation/replebish")
    @Operation(summary = "replenish account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success replenish"),
            @ApiResponse(responseCode = "400", description = "wrong accountId type"),
            @ApiResponse(responseCode = "404", description = "account not found"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public AccountResponseDto replenish(@RequestBody ReplenishRequestDto requestDto) {
        return accountService.replenish(requestDto);
    }

    @GetMapping("/operation/viewBalance")
    @Operation(summary = "view balance account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got balance"),
            @ApiResponse(responseCode = "400", description = "wrong accountId"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public AccountResponseDto viewBalance(@ModelAttribute ViewBalanceRequestDto requestDto) {
        return accountService.viewBalance(requestDto);
    }

    @GetMapping("/get/transaction")
    @Operation(summary = "get transaction with filter")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got transactions"),
            @ApiResponse(responseCode = "400", description = "wrong color"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public List<TransactionResponseDto> getTransaction(@ModelAttribute GetTransactionsRequestDto requestDto) {
        return accountService.getTransactions(requestDto);
    }

    @GetMapping("/get/accounts")
    @Operation(summary = "get all accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "got accounts"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PatchMapping("/operation/transfer")
    @Operation(summary = "transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success transfer"),
            @ApiResponse(responseCode = "400", description = "wrong accountId type"),
            @ApiResponse(responseCode = "404", description = "account not found"),
            @ApiResponse(responseCode = "500", description = "server error")
    })
    public AccountResponseDto transfer(@RequestBody TransferRequestDto requestDto) {
        return accountService.transfer(requestDto);
    }
 }
