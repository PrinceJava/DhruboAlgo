package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
import com.project2.dhrubosalgorithms.model.*;
import com.project2.dhrubosalgorithms.repository.*;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- STUDENT SERVICES ---------

Student Services main focus is to connect database access via JPA and business logic to return to the controller.
This Service includes user, submission, category, and algorithm Repositories as it needs to access data from all tables
All methods in these calls are being called from UserController class

Goal of this page is to
1. create Submission entry, which takes in a submission form as an object, and categoryName and algorithm Name as path variables
2. get all algorithms stored on the Algorithm Table.  Only filter is Algorithm::isPublic, and only returns all public algorithms
3. get all submissions relative to the user that was authenticated via Security Context Holder in MyUserDetails
 */
@Service @Transactional
public class StudentService {
    private UserRepository userRepository;
    private SubmissionRepository submissionRepository;
    private CategoryRepository categoryRepository;
    private AlgorithmRepository algorithmRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setSubmissionRepository(SubmissionRepository submissionRepository){this.submissionRepository = submissionRepository;}
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){this.categoryRepository = categoryRepository;}
    @Autowired
    public void setAlgorithmRepository(AlgorithmRepository algorithmRepository){this.algorithmRepository = algorithmRepository;}


    /**
     * Method will take in a form entry as a JSON object, and pass in two Path Variables from the endpoint as well
     * and create an entry in the Submissions Table
     * @param categoryName - Path Variable from api endpoint to get Category Name
     * @param algorithmName - Path Variable from api endpoint to get Algorithm Name
     * @param submissionObject - Submission Object passed from the SubmissionUpdate Form Class
     * @return - save the submissionObject to the submissionRepository after seting User, Algorithm.  Pass is Null by default.
     */
    public Submissions createSubmissionEntry(String categoryName, String algorithmName, Submissions submissionObject) {
        System.out.println("Calling AdminService saveUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        try{
            Category category = categoryRepository.findByName(categoryName);
            Algorithm algorithm = algorithmRepository.findByName(algorithmName);
            User user = userRepository.findById(userDetails.getUser().getId()).get();

            submissionObject.setUser((User) user);
            submissionObject.setAlgorithm((Algorithm) algorithm);
            submissionObject.setPass(null);
            return submissionRepository.save(submissionObject);
        } catch (NoSuchElementException e){
            throw new InformationNotFoundException("Format was incorrect on your submission");
        }
    }

    /**
     * Method that will return ALL Algorithms in the Algorithm TABLE, with only one file via STREAM if they are public.
     * @return - LIST OF ALGORITHMS after Stream Filter
     */
    public List<Algorithm> getAlgorithms() {
        System.out.println("ADMIN SERVICE - getSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return algorithmRepository.findAll().stream()
                .filter(Algorithm::isPublic).collect(Collectors.toList());
    }

    /**
     * This method was a big AHA moment on functionality of userDetails object that was created for auth.  Instead of using
     * a path variable, we pulled the ID from the suerDetails object.
     * @return - List of Submissions where id field matches JWT userId.
     */
    public List<Submissions> getSubmissions(){
        System.out.println("ADMIN SERVICE - getSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return submissionRepository.findByUserId(userDetails.getUser().getId());
    }
}
