package com.project2.dhrubosalgorithms.security;

import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(email);
        return new MyUserDetails(user);
    }
}