package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.*;
import com.project2.dhrubosalgorithms.model.response.*;
import com.project2.dhrubosalgorithms.service.AdminService;
import com.project2.dhrubosalgorithms.service.AlgorithmService;
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
    private AlgorithmService algorithmService;
    private AddRole addRole;

    @Autowired
    public void setAdminService(AdminService adminService){this.adminService = adminService;}
    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}
    @Autowired
    public void setCategoryService(CategoryService categoryService){this.categoryService = categoryService;}
    @Autowired
    public void setAlgorithmService(AlgorithmService algorithmService){this.algorithmService = algorithmService;}
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

    //----------------------- CRUD FOR CATEGORIES ------------------------------ //

    @PostMapping("/createcategory")
    public ResponseEntity<?>createCategory(@RequestBody Category categoryObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(categoryService.createCategory(categoryObject));
    }

    // get all categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>>getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }
    // get  categoru
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategory(categoryId));
    }
    // put categories
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<?>updateCategory(@PathVariable(value = "categoryId") Long categoryId,
                                           @RequestBody Category categoryObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(categoryService.updateCategory(categoryId, categoryObject));
    }
    // delete categories
    @DeleteMapping("/deletecategory")
    public ResponseEntity<?>deleteCategory(@RequestBody DeleteCategory deleteCategoryForm) {
        categoryService.deleteCategory(deleteCategoryForm.getCategoryId());
        return ResponseEntity.ok().build();
    }

    // ---------------------- CRUD FOR ALGORITHMS ----------------------------- //


    // get all algorithms
    @GetMapping("/categories/{categoryId}/algorithms")
    public ResponseEntity<List<Algorithm>>getAlgorithms(@PathVariable(value = "categoryId") Long categoryId) {
        return ResponseEntity.ok().body(algorithmService.getAlgorithms(categoryId));
    }

    // get an algorithm
    @GetMapping("/categories/{categoryId}/algorithms/{algorithmId}")
    public ResponseEntity<Algorithm> getAlgorithm(@PathVariable(value = "categoryId") Long categoryId,
                                                            @PathVariable(value = "algorithmId") Long algorithmId) {
        return ResponseEntity.ok().body(algorithmService.getAlgorithm(categoryId, algorithmId));
    }

    // create an algorithm
    @PostMapping("/{categoryName}/createalgorithm")
    public ResponseEntity<?>createAlgorithm(@PathVariable(value = "categoryName") String categoryName,
                                            @RequestBody Algorithm algorithmObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(algorithmService.createAlgorithm(categoryName, algorithmObject));
    }

    // update an algorithm
    @PutMapping("/categories/{categoryId}/algorithms/{algorithmId}")
    public ResponseEntity<?>updateAlgorithm(@PathVariable(value = "categoryId") Long categoryId,
                                            @PathVariable(value = "algorithmId") Long algorithmId,
                                           @RequestBody Algorithm algorithmObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(algorithmService.updateAlgorithm(categoryId, algorithmId, algorithmObject));
    }

    // delete an algorithm
    @DeleteMapping("/deletealgorithm")
    public ResponseEntity<?>deleteAlgorithm(@RequestBody DeleteAlgorithm deleteAlgorithmForm) {
        algorithmService.deleteAlgorithm(deleteAlgorithmForm.getCategoryId(), deleteAlgorithmForm.getAlgorithmId());
        return ResponseEntity.ok().build();
    }
}

