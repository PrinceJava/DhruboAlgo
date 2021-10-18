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


@Service
public class UserService {
    // USED FOR AUTHENTICATION SERVICES ON SERVER


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
     * CREATEUSER
     *
     * @param userName     from RegisterForm and parsed out.  Will be set to newUser.setUserName()
     * @param emailAddress from RegisterForm and parsed out.  Will be set to newUser.setEmailAddress()
     * @param password     from RegisterForm and parsed out.  Will be set to newUser.setPassword() through encoder()
     * @param roleName     from RegisterForm and parsed out. Will find role with roleName and assign to newUser
     * @return
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


    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        System.out.println("service calling loginUser ==>");
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String JWT = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(JWT));
    }


    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }
}
