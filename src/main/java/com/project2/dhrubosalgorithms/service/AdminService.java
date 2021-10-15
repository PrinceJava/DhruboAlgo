package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationExistException;
import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.Submissions;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.model.response.DeleteSubmission;
import com.project2.dhrubosalgorithms.repository.*;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import com.project2.dhrubosalgorithms.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service @Transactional
public class AdminService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SubmissionRepository submissionRepository;
    private CategoryRepository categoryRepository;
    private AlgorithmRepository algorithmRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

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
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    public User addUser(User userObject) {
        System.out.println("Calling AdminService saveUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.save(userObject);
    }

    public Role addRole(Role roleObject) {
        System.out.println("Calling AdminService saveRole ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return roleRepository.save(roleObject);
    }

    // METHOD IS A VOID, SO I DON'T NEED TO RETURN ANYTHING OR SAVE WITH TRANSACTIONAL
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

    public Optional<User> getUser(Long userId) {
        System.out.println("ADMIN SERVICE - getUser ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user;
        } else {
            throw new InformationNotFoundException("No user was found");
        }
    }

    public List<User> getUsers() {
        System.out.println("ADMIN SERVICE - getUsers ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder   .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findAll();
    }


    public List<Submissions> getSubmissions(){
        /*

         */
        System.out.println("ADMIN SERVICE - getSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder   .getContext()
                .getAuthentication()
                .getPrincipal();
        return submissionRepository.findAll();
    }
    public List<Submissions> getPendingSubmissions(){
        System.out.println("ADMIN SERVICE - getPendingSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder   .getContext()
                .getAuthentication()
                .getPrincipal();
        return submissionRepository.findByStatusIs("pending");
    }

    /**
     *
     * @param submissionStatus
     * @param submissionPass
     * @param submissionId
     * @return
     */
    public Submissions updateSubmission(String submissionStatus, Boolean submissionPass, Long submissionId ){

        System.out.println("ADMIN SERVICE - updateSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder   .getContext()
                .getAuthentication()
                .getPrincipal();
        try{
            Submissions submission = submissionRepository.findById(submissionId).get();
            submission.setStatus(submissionStatus);
            submission.setPass(submissionPass);
            return submission;
        }catch(NoSuchElementException e){
            throw new InformationNotFoundException("Submission not found");

        }
    }
    public void deleteSubmission (Long submissionId){
        System.out.println("ADMIN SERVICE - deleteSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder   .getContext()
                .getAuthentication()
                .getPrincipal();
        try{
            Submissions submission = submissionRepository.findById(submissionId).get();
            submissionRepository.delete(submission);
        }catch (NoSuchElementException e){
            throw new InformationNotFoundException("Submission not found");

        }
    }


/*    public User updateUserRole(Long userId, Long roleId, User userObject) {
        // authenticate
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        try {
            User user = userRepository.findById(userId).get();
            if (user.getId().equals(user.getId())) {
                throw new InformationExistException("Email " + user.getEmailAddress() +
                        " already exists");
            } else {
                user.setRoleList(userObject.getRoleList());
                return userRepository.save(user);
            }
        }catch (NoSuchElementException e){
            throw new InformationNotFoundException("Email " + userId + "not found");
        }
    }*/
}
