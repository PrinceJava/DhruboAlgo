package com.project2.dhrubosalgorithms.model.response;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
/*
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }*/
}
