package ru.osaulenko.controller;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;
    private final int statusCode;
}
