package com.project2.dhrubosalgorithms.model.response;

import lombok.Data;

@Data
public class RegisterForm {
    private String userName;
    private String emailAddress;
    private String password;
    private String role;
}
