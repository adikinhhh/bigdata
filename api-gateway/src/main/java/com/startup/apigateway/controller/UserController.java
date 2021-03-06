package com.startup.apigateway.controller;

import com.startup.apigateway.dao.DeviceToken;
import com.startup.apigateway.dao.UserLocation;
import com.startup.apigateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(path = "/api/userlocation")
    public ResponseEntity<?> updateUserLocation(@RequestHeader(value = "Authorization") String jwt, @RequestBody UserLocation userLocation) {
        return userService.updateUserLocation(jwt, userLocation);
    }

    @PutMapping(path = "/api/devicetoken")
    public ResponseEntity<?> updateDeviceToken(@RequestHeader(value = "Authorization") String jwt, @RequestBody DeviceToken deviceToken) {
        return userService.updateDeviceToken(jwt, deviceToken);
    }
}
