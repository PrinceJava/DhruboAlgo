package com.project2.dhrubosalgorithms.controller;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- AUTHENTICATION CONTROLLER ---------

Authentication Controller is responsible for initial authentication perposes, and is an exposed endpoint without any restrictions
This is a RestController that is Mapped to "/api"
This Class only instantiate one object of type userService where the authentication methods are locacted.

Goal of this page is to
1. Create users from "api/register" where they can add
    A. username
    B. email Address
    C. password
    D. Assign a role
2. Generate a JWT token through the Login Portal "/api/login"
 */
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

    /**
     * Takes in the registerForm which provides variable from both User and Role classes
     * @param registerForm register is located at "model.response.RegisterForm" and includes
     *                     1. username, email address, password
     *                     2. role name to locate the role object
     * @return JSON object, verifying user was created and role was correctly assigned
     */
    @PostMapping("/register")
    public User createUser(@RequestBody RegisterForm registerForm) {
        System.out.println("controller is calling create user ===>");
        return userService.createUser(registerForm.getUserName(),
                registerForm.getEmailAddress(),registerForm.getPassword(),
                registerForm.getRole());
    }

    /**
     * loginUser method takes in authentication credentials username and password and verifies they match in the User table.
     * once verified, a JWT token will be generated and assigned via Security Context Holder in "security.jwt.JWTUtils"
     * @param loginRequest - form is located at "model.response.LoginRequest" and holds
     *                     username and email fields.
     * @return JSON object of the JWT token passed.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("controller is calling loginUser ===>");
        return userService.loginUser(loginRequest);
    }
}
