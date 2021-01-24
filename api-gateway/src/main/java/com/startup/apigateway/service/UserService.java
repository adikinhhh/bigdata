package com.startup.apigateway.service;

import com.startup.apigateway.dao.User;
import com.startup.apigateway.dto.CredentialsRequest;
import com.startup.apigateway.dto.UserDto;
import com.startup.apigateway.repository.UserRepository;
import com.startup.apigateway.service.exception.UnauthorizedException;
import com.startup.apigateway.service.exception.UserAlreadyExistsException;
import com.startup.apigateway.service.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
