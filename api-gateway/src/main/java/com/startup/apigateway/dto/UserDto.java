package com.startup.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class UserDto {
    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
}
