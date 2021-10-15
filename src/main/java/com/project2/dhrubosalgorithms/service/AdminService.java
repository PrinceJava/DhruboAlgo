package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationExistException;
import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.repository.RoleRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import com.project2.dhrubosalgorithms.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service @Transactional
public class AdminService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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


    public User addUser(User userObject) {
        System.out.println("Calling AdminService saveUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.save(userObject);
    }

    public Role addRole(Role roleObject) {
        System.out.println("Calling AdminService saveRole ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return roleRepository.save(roleObject);
    }

    // METHOD IS A VOID, SO I DON'T NEED TO RETURN ANYTHING OR SAVE WITH TRANSACTIONAL
    public void addUserRole(String username, String roleName) {
        System.out.println("ADMIN SERVICE - addRoleToUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findUserByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public Optional<User> getUser(Long userId) {
        System.out.println("ADMIN SERVICE - getUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user;
        } else {
            throw new InformationNotFoundException("No user was found");
        }
    }

    public List<User> getUsers() {
        System.out.println("ADMIN SERVICE - getUsers ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder   .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findAll();
    }



/*    public User updateUserRole(Long userId, Long roleId, User userObject) {
        // authenticate
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        try {
            User user = userRepository.findById(userId).get();
            if (user.getId().equals(user.getId())) {
                throw new InformationExistException("Email " + user.getEmailAddress() +
                        " already exists");
            } else {
                user.setRoleList(userObject.getRoleList());
                return userRepository.save(user);
            }
        }catch (NoSuchElementException e){
            throw new InformationNotFoundException("Email " + userId + "not found");
        }
    }*/
}
