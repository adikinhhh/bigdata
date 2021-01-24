package com.startup.apigateway.fcm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PushNotificationResponse {
    private int status;
    private String message;
}
