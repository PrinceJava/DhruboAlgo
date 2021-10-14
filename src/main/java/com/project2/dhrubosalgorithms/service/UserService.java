package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.repository.RoleRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;





    // --------------------------------- METHOD FROM USER DETAILS SERVICE --------------- //
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAddress(email);
        if (user == null) {
            throw new UsernameNotFoundException("Username is not found in the dB");
        } else {
            return new MyUserDetails(user);
        }
    }
}
