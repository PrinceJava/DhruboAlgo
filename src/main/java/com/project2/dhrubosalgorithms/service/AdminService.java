package com.project2.dhrubosalgorithms.service;


import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.Submissions;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.repository.*;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- ADMIN SERVICES ---------

Admin Services houses the majority of App Business Logic.  This logic is intended for Admin used to update users, add users,
create categories and algorithms and update and delete submissions from the submissions table. Most methods will save/update/delete
to a repository, and included repositories are userRepository, roleRepository, and submissionsRepository.  Algorithm and Category
repositories are not included as they are housed in CategoryService and AlgorithmService.

Goal of this page is to
1. create users and add them to the USER TABLE
2. create roles and add them to the ROLE TABLE
3. add a role to the ArrayList<> or ROLES inside the ManyToMany Field on USER TABLE
4. get a single user based off Path Variable userId
5. get a List of all users, for purpose of demonstration we will show all ROLES, CATEGORIES, and ALGORITHMS as nested Lists
6. get all Submissions housed on the SUBMISSIONS TABLE
7. get all Pending Submissions on the SUBMISSIONS TABLE for grading tasks
8. update a submission, changing PASS from null to True/False and Status from Pending to Completed
9. delete a submission from the SUBMISSIONS TABLE
 */

@Service
@Transactional
public class AdminService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SubmissionRepository submissionRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSubmissionRepository(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /**
     * Called from Admin Controller, creates a user based off ARGS Constructor, leaving all LISTS NULL
     * @param userObject - userObject from JSON BODY including variables in the User Constructor
     * @return - save the new user to the userRepository and USER TABLE
     */
    public User addUser(User userObject) {
        System.out.println("Calling AdminService saveUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.save(userObject);
    }

    /**
     * Called from Admin Controller, creates a role based off ARGS Constructor
     * @param roleObject - roleObject from JSON BODY including variables in the Role Constructor
     * @return - save the new role to the roleRepository and ROLE TABLE
     */
    public Role addRole(Role roleObject) {
        System.out.println("Calling AdminService saveRole ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return roleRepository.save(roleObject);
    }

    /**
     * Called from Admin Controller, add Role Object to User Object
     * @param username - passed from Add Role Form as userName field
     * @param roleName - passed from Add Role Form as roleName field
     */
    public void addUserRole(String username, String roleName) {
        System.out.println("ADMIN SERVICE - addRoleToUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findUserByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        user.setRoles(user.getRoles());
    }

    /**
     * Called from Admin Controller, get user based off passed userId field
     * @return - return the user Object found if present, otherwise throw error
     */
    public Optional<User> getUser(Long userId) {
        System.out.println("ADMIN SERVICE - getUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user;
        } else {
            throw new InformationNotFoundException("No user was found");
        }
    }

    /**
     * @return - List of Users using method findAll()
     */
    public List<User> getUsers() {
        System.out.println("ADMIN SERVICE - getUsers ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findAll();
    }


    /**
     * @return - List of Submissions using method findAll()
     */
    public List<Submissions> getSubmissions() {
        System.out.println("ADMIN SERVICE - getSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return submissionRepository.findAll();
    }

    /**
     * Same process as getSubmissions with the .findByStatusIs Repository Method to get all ungraded submissions
     * @return - List of Submissions where Status == "pending"
     */
    public List<Submissions> getPendingSubmissions() {
        System.out.println("ADMIN SERVICE - getPendingSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return submissionRepository.findByStatusIs("pending");
    }

    /**
     * @param submissionStatus - JSON BODY OBJECT FROM SUBMISSION UPDATE FORM - String
     * @param submissionPass - JSON BODY OBJECT FROM SUBMISSION UPDATE FORM - Boolean
     * @param submissionId - JSON BODY OBJECT FROM SUBMISSION UPDATE FORM - Long
     * @return - once submission object is found via findById(), it will update status and pass and return it
     */
    public Submissions updateSubmission(String submissionStatus, Boolean submissionPass, Long submissionId) {

        System.out.println("ADMIN SERVICE - updateSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            Submissions submission = submissionRepository.findById(submissionId).get();
            submission.setStatus(submissionStatus);
            submission.setPass(submissionPass);
            return submission;
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("Submission not found");

        }
    }

    /**
     * @param submissionId - - JSON BODY OBJECT FROM DELETE SUBMISSION FORM - Long
     */
    public void deleteSubmission(Long submissionId) {
        System.out.println("ADMIN SERVICE - deleteSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            Submissions submission = submissionRepository.findById(submissionId).get();
            submissionRepository.delete(submission);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("Submission not found");

        }
    }
}
