package com.project2.dhrubosalgorithms.security;

import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final User user;
    private String userName;
    private String password;
    private String emailAddress;

    // LOOK INTO GRANTED AUTHORITIES

    // CONSTRUCTOR THAT THE myUserDetailsService Returns from the UserService Interface Method
    public MyUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    // -------------------------------- USER DETAILS INTERFACE ------------------------------------------//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // ------------------ //
        // Takes all the role names from ROLE class
        // and adds them to a list of authorities
        // which will be used in HTTP configure security
        Collection<Role> roles = null;
            List<GrantedAuthority> authorities = new ArrayList<>();
        assert false;
        for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return authorities;
        }
        // ORIGINAL IDEA
        // return new HashSet<GrantedAuthority>();


    // NEED TO WORK ON GRANTED AUTHORITIES tomorrow

    // CAUSED A LOT OF ERRORS IF YOU DON"T HAVE
    // http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    // SET UP IN SECURITY CONFIGURER
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}