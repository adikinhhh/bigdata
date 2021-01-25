package com.startup.apigateway.controller;

import com.startup.apigateway.fcm.service.PushNotificationService;
import com.startup.apigateway.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static com.startup.apigateway.security.Roles.ROLE_USER;

@RestController
@Secured(ROLE_USER)
public class ParkingLotController {
    @Value("${parkinglotserver.host}")
    private String parkinglotserverhost;

    @Value("${signature.secret}")
    private String secret;

    private HttpServletRequest httpServletRequest;

    private final UserService userService;

    private final PushNotificationService pushNotificationService;

    @Autowired
    public ParkingLotController(UserService userService, PushNotificationService pushNotificationService) {
        this.userService = userService;
        this.pushNotificationService = pushNotificationService;
    }

    @GetMapping(value = "/api/parkinglots/")
    public ResponseEntity<?> getAllParkingLots(ProxyExchange<byte[]> proxyExchange) {
        return proxyExchange.uri(parkinglotserverhost + "/api/parkinglots/").get();
    }

    @GetMapping(value = "/api/parkinglots/distance")
    public ResponseEntity<?> getAllParkingLotsByDistance(HttpServletRequest httpServletRequest, ProxyExchange<byte[]> proxyExchange) {
        return proxyExchange.uri(parkinglotserverhost + "/api/parkinglots/distance?" + httpServletRequest.getQueryString()).get();
    }

    @PostMapping(path = "/api/parkinglots/")
    public ResponseEntity<?> postParkingLots(ProxyExchange<byte[]> proxyExchange){
        return proxyExchange.uri(parkinglotserverhost + "/api/parkinglots/").post();
    }

    @DeleteMapping(path = "/api/parkinglots/{id}")
    public ResponseEntity<?> deleteParkingLots(@PathVariable String id, ProxyExchange<byte[]> proxyExchange) {
        return proxyExchange.uri(parkinglotserverhost + "/api/parkinglots/" + id).delete();
    }

    @PatchMapping(path = "/api/parkinglots/{id}")
    public ResponseEntity<?> updateParkingLots(@PathVariable String id, @RequestBody String body, ProxyExchange<byte[]> proxyExchange) {
        // get all users which are 1km from that location, send a notification to them
        pushNotificationService.sendPushNotificationForFreeParkingLot(id, body);
        return proxyExchange.uri(parkinglotserverhost + "/api/parkinglots/" + id).body(body).patch();
    }

    private String getCurrentUserId(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt.substring(7)).getBody();
        return userService.getUserId(claims.getSubject());
    }
}
