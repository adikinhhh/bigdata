package com.startup.apigateway.controller;

import com.startup.apigateway.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.startup.apigateway.security.Roles.ROLE_USER;

@RestController
@Secured(ROLE_USER)
public class IllegalParkingEventController {

    @Value("${illegalparkingserver.host}")
    private String illegalparkinghost;

    @Value("${signature.secret}")
    private String secret;

    private HttpServletRequest httpServletRequest;

    private final UserService userService;

    @Autowired
    public IllegalParkingEventController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/illegalparkingevent")
    public ResponseEntity<?> getAllIllegalParkingEvents(HttpServletRequest httpServletRequest, ProxyExchange<byte[]> proxyExchange) {
        if(!Objects.isNull(httpServletRequest.getQueryString())) {
            return proxyExchange.uri(illegalparkinghost + "/api/illegalparkingevent?" + httpServletRequest.getQueryString()).get();
        }
        return proxyExchange.uri(illegalparkinghost + "/api/illegalparkingevent").get();
    }

    @PostMapping(path = "/api/illegalparkingevent")
    public ResponseEntity<?> postIllegalParkingEvent(ProxyExchange<byte[]> proxyExchange){
        return proxyExchange.uri(illegalparkinghost + "/api/illegalparkingevent").post();
    }

    @GetMapping(path = "/api/illegalparkingevent/{id}")
    ResponseEntity<?> getIllegalParkingEventById(@PathVariable String id, ProxyExchange<byte[]> proxyExchange) {
        return proxyExchange.uri(illegalparkinghost + "/api/illegalparkingevent/"+ id).get();
    }

    @DeleteMapping(path = "/api/illegalparkingevent/{id}")
    ResponseEntity<?> deleteIllegalParkingEvent(@PathVariable String id, ProxyExchange<byte[]> proxyExchange) {
        return proxyExchange.uri(illegalparkinghost + "/api/illegalparkingevent/"+ id).delete();
    }

    @PutMapping(path = "/api/illegalparkingevent/{id}")
    ResponseEntity<?> updateIllegalParkingReputationScoreById(@PathVariable String id, @RequestParam String type, ProxyExchange<byte[]> proxyExchange) {
        return proxyExchange.uri(illegalparkinghost + "/api/illegalparkingevent/"+ id + "?type=" + type).put();
    }

    private String getCurrentUserId(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt.substring(7)).getBody();
        return userService.getUserId(claims.getSubject());
    }
}
