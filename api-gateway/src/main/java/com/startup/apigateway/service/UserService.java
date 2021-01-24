package com.startup.apigateway.service;

import com.startup.apigateway.dao.DeviceToken;
import com.startup.apigateway.dao.User;
import com.startup.apigateway.dao.UserLocation;
import com.startup.apigateway.dto.CredentialsRequest;
import com.startup.apigateway.dto.UserDto;
import com.startup.apigateway.repository.UserRepository;
import com.startup.apigateway.service.exception.UnauthorizedException;
import com.startup.apigateway.service.exception.UserAlreadyExistsException;
import com.startup.apigateway.service.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${signature.secret}")
    private String secret;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validatePassword(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User cannot be found in database");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("The password is incorrect");
        }
    }

    public UserDto createUser(CredentialsRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = User.builder()
                .email(request.getEmail())
                .password(hashedPassword)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .deviceToken(null)
                .userLocation(null)
                .build();
        User userSaved = userRepository.save(newUser);

        log.info("User {} has been saved in database", userSaved.getId());
        return new UserDto(userSaved.getId(), userSaved.getEmail(), userSaved.getFirstName(), userSaved.getLastName());
    }

    public String getUserId(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User cannot be found in database");
        }
        return user.getId();
    }

    public ResponseEntity<?> updateUserLocation(String jwt, UserLocation userLocation) {
        //decode jwt to find userId
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt.substring(7)).getBody();
        String userId = getUserId(claims.getSubject());
        Optional<User> crtUser = userRepository.findById(userId);
        if(crtUser.isPresent()) {
            userLocation.setUserLocationTimestamp(new Date());
            crtUser.get().setUserLocation(userLocation);
            User savedUser = userRepository.save(crtUser.get());
            return ResponseEntity.ok(userLocation);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> updateDeviceToken(String jwt, DeviceToken deviceToken) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt.substring(7)).getBody();
        String userId = getUserId(claims.getSubject());
        Optional<User> crtUser = userRepository.findById(userId);
        if(crtUser.isPresent()) {
            crtUser.get().setDeviceToken(deviceToken.getDeviceToken());
            User savedUser = userRepository.save(crtUser.get());
            return ResponseEntity.ok("Device token added.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
