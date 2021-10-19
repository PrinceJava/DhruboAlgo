package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Submissions;
import com.project2.dhrubosalgorithms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- USER CONTROLLER ---------

User Controller is responsible for all actions that can be taken by users outside of original Authentication public endpoints
User Controller is mapped to "/users" and only creates StudentServices object where all Service business logic is housed.
All methods return ResponseEntities with either .ok() or .created() with a passed URI file path.


Goal of this page is to
1. Create a Submission and add it to the Submission TABLE via the submissionsRepository in StudentServices
2. Get all algorithms from the studentServices and return a LIST of algorithms that have isPublic::true;
3. Get a list of all submissions POSTED by USER.  USER is defined via JWT TOKEN subject.
 */

@RestController
@RequestMapping("/users")
public class UserController {


    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/{categoryName}/{algorithmName}/submit")
    public ResponseEntity<?> createSubmissionEntry(
            @PathVariable(value = "categoryName") String categoryName,
            @PathVariable(value = "algorithmName") String algorithmName,
            @RequestBody Submissions submissionsObject) {
        studentService.createSubmissionEntry(categoryName, algorithmName, submissionsObject);
        URI uri = URI.create("/com.project2.dhrubosalgorithms/controller/usercontroller/");
        return ResponseEntity.created(uri).build();
    }

    // ---------------------- CRUD FOR ALGORITHMS ----------------------------- //

    @GetMapping("/algorithms")
    public ResponseEntity<List<Algorithm>> getAlgorithms() {
        return ResponseEntity.ok().body(studentService.getAlgorithms());
    }

    @GetMapping("/getsubmissions")
    public ResponseEntity<List<Submissions>> getSubmissions() {
        return ResponseEntity.ok().body(studentService.getSubmissions());
    }
}
