package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.service.AdminService;
import com.project2.dhrubosalgorithms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService){this.adminService = adminService;}
    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}


    @GetMapping("/getusers")
    public List<User> getUsers(){
        return adminService.getUsers();
    }

    @PostMapping("/user/add")

/*
    @PostMapping ("/adduserrole/user/{userName}/role/{roleName}")
    public ResponseEntity<> updateUserRole(@PathVariable(value = "userName") String userName,
                               @PathVariable(value = "roleName") String roleName,
                               @RequestBody User userObject){
            adminService.addUserRole(userName,roleName);

    }
*/

}

