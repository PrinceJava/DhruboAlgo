package com.project2.dhrubosalgorithms.controller;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- ADMIN CONTROLLER ---------

Admin Controller is responsible for interacting with the View endpoints that require admin privileges
This is a RestController that is Mapped to "/admin"

Goal of this page is to
1. CRUD Functions on Users
2. CRUD Functions on Categories
3. CRUD Functions on Algorithms
 */

import com.project2.dhrubosalgorithms.model.*;
import com.project2.dhrubosalgorithms.model.response.*;
import com.project2.dhrubosalgorithms.service.AdminService;
import com.project2.dhrubosalgorithms.service.AlgorithmService;
import com.project2.dhrubosalgorithms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    /* Objects needed for Admin Controller and Autowired annotation */
    private AdminService adminService;
    private CategoryService categoryService;
    private AlgorithmService algorithmService;


    @Autowired
    public void setAdminService(AdminService adminService){this.adminService = adminService;}
    @Autowired
    public void setCategoryService(CategoryService categoryService){this.categoryService = categoryService;}
    @Autowired
    public void setAlgorithmService(AlgorithmService algorithmService){this.algorithmService = algorithmService;}

    //----------------------- CRUD FOR USER ------------------------------ //

    /**
     * getUsers does not take any parameters, and calls Admin Service getUsers
     * @return value is a Response Entity of 200 with the body being the return
     */
    @GetMapping("/getusers")
    public ResponseEntity<List<User>>getUsers(){
        return ResponseEntity.ok().body(adminService.getUsers());
    }

    /**
     *  getUser takes one parameter, and calls Admin Service get User
     * @param userId - User ID is pulled from the URL as a Path Variable
     * @return value is a Response Entity of created with uri and the body being the return
     */
    @GetMapping("/getUser/{userId}")
    public Optional<User> getUser(@PathVariable(value = "userId") Long userId){
        return adminService.getUser(userId);
    }

    /**
     * Method addUser will take passed JSON body of an Object User and create one and save it to the database
     * @param user user is the object that is passed to Admin Service which will include:
     *             name, username, email address, password
     * @return value is a Response Entity of created with uri and the body being the return
     */
    @PostMapping("/user/add")
    public ResponseEntity<User>addUser(@RequestBody User user){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(adminService.addUser(user));
    }

    /**
     * Method addRole will take passed JSON body of an Object User and create one and save it to the database
     * @param role role is the object that is passed to Admin Service which will include:
     *             name, description
     * @return value is a Response Entity of created with uri and the body being the return
     */
    @PostMapping("/role/add")
    public ResponseEntity<Role>addRole(@RequestBody Role role){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(adminService.addRole(role));
    }

    /**
     * Method addUserRole take JSON body from addRoleForm, finds the respective user and role and adds the role to the user.
     * @param addRoleForm addRoleForm is found in "model.response" and is used to collect username and role name
     * @return value is a Response Entity of 200 with the body being the return
     */
    @PostMapping("/role/adduserrole")
    public ResponseEntity<?>addUserRole(@RequestBody AddRole addRoleForm){
        adminService.addUserRole(addRoleForm.getUserName(), addRoleForm.getRoleName());
        return ResponseEntity.ok().build();
    }
    //----------------------- CRUD FOR SUBMISSIONS ------------------------------ //

    /**
     * getSubmissions does not take any parameters, and calls Admin Service getSubmissions
     * @return value is a Response Entity of 200 with the body being the return
     */
    @GetMapping("/getsubmissions")
    public ResponseEntity<List<Submissions>>getSubmissions(){
        return ResponseEntity.ok().body(adminService.getSubmissions());
    }

    /**
     * getSubmission does not take any parameters, and calls Admin Service getPendingSubmissions
     * @return value is a Response Entity of 200 with the body being the return of all PENDING submissions
     */
    @GetMapping("/pending")
    public ResponseEntity<List<Submissions>>getPendingSubmissions(){
        return ResponseEntity.ok().body(adminService.getPendingSubmissions());
    }

    /**
     * updateSubmissions takes in the SubmissionUpdate form variables and updates the selected Submission
     * goal of this method is to change the status from pending to completed and set the pass variable
     * @param updateForm - updateForm located in "model.response.Submission.Update" and will  be the body of the JSON object
     * @return value is a Response Entity of created with uri and the body being the return
     */
    @PutMapping("/submissions")
    public ResponseEntity<?>updateSubmission(@RequestBody SubmissionUpdate updateForm){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(adminService.updateSubmission(updateForm.getStatus(), updateForm.getPass(), updateForm.getSubmissionId()));
    }

    /**
     * deleteSubmission takes in the DeleteSubmission form variables and removes the selected Submission
     * @param updateForm - updateForm located in "model.response.DeleteForm" and will be the body of the JSON object
     * @return value is a Response Entity of 200 with the body being the return of all PENDING submissions
     */
    @DeleteMapping("/submissions")
    public ResponseEntity<?>deleteSubmission(@RequestBody DeleteSubmission updateForm) {
        adminService.deleteSubmission(updateForm.getSubmissionId());
        return ResponseEntity.ok().build();
    }

    //----------------------- CRUD FOR CATEGORIES ------------------------------ //

    // POST a new Category
    @PostMapping("/createcategory")
    public ResponseEntity<?>createCategory(@RequestBody Category categoryObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(categoryService.createCategory(categoryObject));
    }

    // GET all Categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>>getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }
    // GET a single Category
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategory(categoryId));
    }
    // Update a single Category
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<?>updateCategory(@PathVariable(value = "categoryId") Long categoryId,
                                           @RequestBody Category categoryObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(categoryService.updateCategory(categoryId, categoryObject));
    }
    // Delete a single Category
    @DeleteMapping("/deletecategory")
    public ResponseEntity<?>deleteCategory(@RequestBody DeleteCategory deleteCategoryForm) {
        categoryService.deleteCategory(deleteCategoryForm.getCategoryId());
        return ResponseEntity.ok().build();
    }

    // ---------------------- CRUD FOR ALGORITHMS ----------------------------- //


    // GET all algorithms
    @GetMapping("/categories/{categoryId}/algorithms")
    public ResponseEntity<List<Algorithm>>getAlgorithms(@PathVariable(value = "categoryId") Long categoryId) {
        return ResponseEntity.ok().body(algorithmService.getAlgorithms(categoryId));
    }

    // GET an algorithm
    @GetMapping("/categories/{categoryId}/algorithms/{algorithmId}")
    public ResponseEntity<Algorithm> getAlgorithm(@PathVariable(value = "categoryId") Long categoryId,
                                                            @PathVariable(value = "algorithmId") Long algorithmId) {
        return ResponseEntity.ok().body(algorithmService.getAlgorithm(categoryId, algorithmId));
    }

    // CREATE an algorithm
    @PostMapping("/{categoryName}/createalgorithm")
    public ResponseEntity<?>createAlgorithm(@PathVariable(value = "categoryName") String categoryName,
                                            @RequestBody Algorithm algorithmObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(algorithmService.createAlgorithm(categoryName, algorithmObject));
    }

    // UPDATE an algorithm
    @PutMapping("/categories/{categoryId}/algorithms/{algorithmId}")
    public ResponseEntity<?>updateAlgorithm(@PathVariable(value = "categoryId") Long categoryId,
                                            @PathVariable(value = "algorithmId") Long algorithmId,
                                           @RequestBody Algorithm algorithmObject){
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/admincontroller/");
        return ResponseEntity.created(uri).body(algorithmService.updateAlgorithm(categoryId, algorithmId, algorithmObject));
    }

    // DELETE an algorithm
    @DeleteMapping("/deletealgorithm")
    public ResponseEntity<?>deleteAlgorithm(@RequestBody DeleteAlgorithm deleteAlgorithmForm) {
        algorithmService.deleteAlgorithm(deleteAlgorithmForm.getCategoryId(), deleteAlgorithmForm.getAlgorithmId());
        return ResponseEntity.ok().build();
    }
}

