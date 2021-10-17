package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.Submissions;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.AddRole;
import com.project2.dhrubosalgorithms.model.response.DeleteSubmission;
import com.project2.dhrubosalgorithms.model.response.SubmissionUpdate;
import com.project2.dhrubosalgorithms.service.AdminService;
import com.project2.dhrubosalgorithms.service.CategoryService;
import com.project2.dhrubosalgorithms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private AdminService adminService;
    private CategoryService categoryService;
    private AddRole addRole;

    @Autowired
    public void setAdminService(AdminService adminService){this.adminService = adminService;}
    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}
    @Autowired
    public void setCategoryService(CategoryService categoryService){this.categoryService = categoryService;}

    @GetMapping("/getusers")
    public ResponseEntity<List<User>>getUsers(){
        return ResponseEntity.ok().body(adminService.getUsers());
    }

    @GetMapping("/getUser/{userId}")
    public Optional<User> getUser(@PathVariable(value = "userId") Long userId){
        return adminService.getUser(userId);
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

    @PostMapping("/role/adduserrole")
    public ResponseEntity<?>addUserRole(@RequestBody AddRole addRoleForm){
        adminService.addUserRole(addRoleForm.getUserName(), addRoleForm.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getsubmissions")
    public ResponseEntity<List<Submissions>>getSubmissions(){
        return ResponseEntity.ok().body(adminService.getSubmissions());
    }

    //TODO get all pending submissions
    //TODO include Admin Service and Admin Controller
    //TODO PUT and update status and pass to boolean (Raul is going to create a form)

    @GetMapping("/pending")
    public ResponseEntity<List<Submissions>>getPendingSubmissions(){
        return ResponseEntity.ok().body(adminService.getPendingSubmissions());
    }

    @PutMapping("/submissions")
    public ResponseEntity<?>updateSubmission(@RequestBody SubmissionUpdate updateForm){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(adminService.updateSubmission(updateForm.getStatus(), updateForm.getPass(), updateForm.getSubmissionId()));
    }
    @DeleteMapping("/submissions")
    public ResponseEntity<?>deleteSubmission(@RequestBody DeleteSubmission updateForm) {
        adminService.deleteSubmission(updateForm.getSubmissionId());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createcategory")
    public ResponseEntity<?>createCategory(@RequestBody Category categoryObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(categoryService.createCategory(categoryObject));

    }
}

