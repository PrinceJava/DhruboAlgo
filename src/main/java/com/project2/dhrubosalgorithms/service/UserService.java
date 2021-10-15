package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationExistException;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.LoginRequest;
import com.project2.dhrubosalgorithms.model.response.LoginResponse;
import com.project2.dhrubosalgorithms.repository.RoleRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import com.project2.dhrubosalgorithms.security.jwt.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){this.roleRepository = roleRepository;}
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder = passwordEncoder;}
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager){this.authenticationManager = authenticationManager;}
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService){this.userDetailsService = userDetailsService;}
    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils){this.jwtUtils = jwtUtils;}


    public User createUser(User userObject) {
        System.out.println("service is calling createUser==>");
        // if user not exists by the email
        // then create the user in the db
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            Role role = roleRepository.findById(2L).get();
            userObject.getRoles().add(role);
            userObject.setRoles(userObject.getRoles());
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("user with the email address " +
                    userObject.getEmailAddress() + " already exists");
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
