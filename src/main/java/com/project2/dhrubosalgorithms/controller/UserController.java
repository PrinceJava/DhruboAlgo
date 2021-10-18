package com.project2.dhrubosalgorithms.controller;

import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Submissions;
import com.project2.dhrubosalgorithms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
