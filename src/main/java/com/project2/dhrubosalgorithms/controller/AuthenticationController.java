package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.LoginRequest;
import com.project2.dhrubosalgorithms.model.response.RegisterForm;
import com.project2.dhrubosalgorithms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}

    @PostMapping("/register")
    public User createUser(@RequestBody RegisterForm registerForm) {
        System.out.println("controller is calling create user ===>");
        return userService.createUser(registerForm.getUserName(),
                registerForm.getEmailAddress(),registerForm.getPassword(),
                registerForm.getRole());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }
}
