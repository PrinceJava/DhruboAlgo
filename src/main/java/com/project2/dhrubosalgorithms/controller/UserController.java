package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Submissions;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.LoginRequest;
import com.project2.dhrubosalgorithms.model.response.RegisterForm;
import com.project2.dhrubosalgorithms.service.StudentService;
import com.project2.dhrubosalgorithms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private StudentService studentService;
    private AuthenticationManager authenticationManager;
    @Autowired
    public void setUserService(UserService userService){this.userService = userService;}
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager){this.authenticationManager=authenticationManager;}
    @Autowired
    public void setStudentService(StudentService studentService){this.studentService = studentService;}



    @PostMapping("/{userId}/{categoryName}/{algorithmName}/submit")
    public ResponseEntity<?> createSubmissionEntry(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "categoryName") String categoryName,
            @PathVariable(value = "algorithmName") String algorithmName,
            @RequestBody Submissions submissionsObject){
        studentService.createSubmissionEntry(userId,categoryName,algorithmName,submissionsObject);
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/usercontroller/");
        return ResponseEntity.created(uri).build();
    }

    // ---------------------- CRUD FOR ALGORITHMS ----------------------------- //

    @GetMapping("/algorithms")
    public ResponseEntity<List<Algorithm>>getAlgorithms(){
        return ResponseEntity.ok().body(studentService.getAlgorithms());
    }



}
