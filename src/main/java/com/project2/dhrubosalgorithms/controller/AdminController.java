package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.AddRole;
import com.project2.dhrubosalgorithms.service.AdminService;
import com.project2.dhrubosalgorithms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;
    private AddRole addRole;

    @Autowired
    public void setAdminService(AdminService adminService){this.adminService = adminService;}
    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}


    @GetMapping("/getusers")
    public ResponseEntity<List<User>>getUsers(){
        return ResponseEntity.ok().body(adminService.getUsers());
    }

    @PostMapping("/user/add")
    public ResponseEntity<User>addUser(@RequestBody User user){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(adminService.addUser(user));
    }

    @PostMapping("/role/add")
    public ResponseEntity<Role>addRole(@RequestBody Role role){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(adminService.addRole(role));
    }

    @PostMapping("/role/addUserRole")
    public ResponseEntity<?>addUserRole(@RequestBody AddRole addRoleForm){
        adminService.addUserRole(addRoleForm.getUserName(), addRoleForm.getRoleName());
        return ResponseEntity.ok().build();
    }

/*
    @PostMapping ("/adduserrole/user/{userName}/role/{roleName}")
    public ResponseEntity<> updateUserRole(@PathVariable(value = "userName") String userName,
                               @PathVariable(value = "roleName") String roleName,
                               @RequestBody User userObject){
            adminService.addUserRole(userName,roleName);

    }
*/

}

