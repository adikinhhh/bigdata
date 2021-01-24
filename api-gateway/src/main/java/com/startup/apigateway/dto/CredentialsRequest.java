package com.startup.apigateway.dto;

import lombok.*;

@NoArgsConstructor
@Data
public class CredentialsRequest {

    @NonNull
    String email;

    @ToString.Exclude
    @NonNull
    String password;

    String firstName;
    String lastName;
}
