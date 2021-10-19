package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationExistException;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.LoginRequest;
import com.project2.dhrubosalgorithms.model.response.LoginResponse;
import com.project2.dhrubosalgorithms.repository.RoleRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import com.project2.dhrubosalgorithms.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- USER SERVICES ---------

User Service is used for original authentication purposes.  Methods included are findUserByEmailAddress, Login, and Register.
This Service takes in userRepository, roleRepository, passwordEncoder, authenticationManager, userDetailsService, and jwtUtils
for purposes of Encoding Passwords, and Generating JWT Tokens based of Authentication Manager and JWTUtilis.

Goal of this page is to
1. create Users based off /api/register API endpoint
2. log in users based off credentials and Authentication Manager, and return a JWT token generated including header, subject, and signature
3. get all submissions relative to the user that was authenticated via Security Context Holder in MyUserDetails
 */

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * CREATE USER
     *
     * @param userName     from RegisterForm and parsed out.  Will be set to newUser.setUserName()
     * @param emailAddress from RegisterForm and parsed out.  Will be set to newUser.setEmailAddress()
     * @param password     from RegisterForm and parsed out.  Will be set to newUser.setPassword() through encoder()
     * @param roleName     from RegisterForm and parsed out. Will find role with roleName and assign to newUser
     * @return - SAVE NEW USER to USER TABLE
     */
    public User createUser(String userName, String emailAddress, String password, String roleName) {
        System.out.println("service is calling createUser==>");
        // if user not exists by the email
        // then create the user in the db

        if (!userRepository.existsByEmailAddress(emailAddress)) {

            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setEmailAddress(emailAddress);
            newUser.setPassword(passwordEncoder.encode(password));
            Role role = roleRepository.findByName(roleName);
            newUser.getRoles().add(role);
            newUser.setRoles(newUser.getRoles());
            return userRepository.save(newUser);
//            return newUser;
        } else {
            throw new InformationExistException("user with the email address " +
                    emailAddress + " already exists");
        }
    }


    /**
     * LOGIN USER
     * Method calls the Authentication Manager object, and implements the authenticate method taking in the past object
     * from the Login Request Form, including user Email and Password.  First will find the user, and generate a TOKEN from
     * JWTUtils class via method generateToken
     * @param loginRequest - PASSED JSON OBJECT FROM LOGIN REQUEST Form - String Username String Email Address
     * @return - Response Entity.ok with the new JWT token
     */
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        System.out.println("service calling loginUser ==>");
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String JWT = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(JWT));
    }


    // Implemented method to find user by passed String email Address.
    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }
}
