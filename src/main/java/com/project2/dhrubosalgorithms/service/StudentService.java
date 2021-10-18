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
    public List<Algorithm> getAlgorithms() {
        System.out.println("ADMIN SERVICE - getSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return algorithmRepository.findAll().stream()
                .filter(Algorithm::isPublic).collect(Collectors.toList());
    }
    public List<Submissions> getSubmissions(){
        System.out.println("ADMIN SERVICE - getSubmissions ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return submissionRepository.findByUserId(userDetails.getUser().getId());
    }
}
