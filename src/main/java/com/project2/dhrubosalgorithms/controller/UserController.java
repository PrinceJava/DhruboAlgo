package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.LoginRequest;
import com.project2.dhrubosalgorithms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User createUser(@RequestBody User userObject) {
        System.out.println("controller is calling create user ===>");
        return userService.createUser(userObject);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }


}
