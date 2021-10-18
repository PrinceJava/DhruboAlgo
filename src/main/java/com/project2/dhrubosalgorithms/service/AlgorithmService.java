package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationExistException;
import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.repository.*;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import com.project2.dhrubosalgorithms.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AlgorithmService {
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

    //TODO CREATE get Algorithm and Algorithms, Delete Algorithm, and Update Algorithm methods in Controller and Service

    public List<Algorithm> getAlgorithms(Long categoryId) {
        System.out.println("service calling getAlgorithms ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
                return category.get().getAlgorithms();
        } else {
            throw new InformationNotFoundException("No Categories were found in the database");
        }
    }
    public Algorithm createAlgorithm(String categoryName, Algorithm algorithmObject) {
        System.out.println("Calling AlgorithmService createAlgorithm ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        try{
            Category category = categoryRepository.findByName(categoryName);
            algorithmObject.setCategory(category);
            return algorithmRepository.save(algorithmObject);
        }catch(NoSuchElementException e){
            throw new InformationNotFoundException("category with name - " + categoryName + " not found");
        }
    }

    public Algorithm getAlgorithm(Long categoryId, Long recipeId) {
        System.out.println("service calling getCategoryRecipe ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder .getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Optional<Algorithm> algorithm = algorithmRepository.findByCategoryId(categoryId).stream().filter(
                    p -> p.getId().equals(recipeId)).findFirst();
            if (algorithm.isEmpty()) {
                throw new InformationNotFoundException("recipe with id " + recipeId + " not found");
            } else {
                return algorithm.get();
            }
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    public Algorithm updateAlgorithm(Long categoryId, Long algorithId, Algorithm algorithmObject) {
        System.out.println("service calling updateCategory ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder .getContext()
                .getAuthentication()
                .getPrincipal();
        try{
                Algorithm updateAlgorithm = algorithmRepository.findByCategoryId(categoryId)
                        .stream()
                        .filter(p -> p.getId().equals(algorithId))
                        .findFirst()
                        .get();
                updateAlgorithm.setName(algorithmObject.getName());
                updateAlgorithm.setDescription(algorithmObject.getDescription());
                updateAlgorithm.setDifficulty(algorithmObject.getDifficulty());
                updateAlgorithm.setHints(algorithmObject.getHints());
                updateAlgorithm.setTimeComplexity(algorithmObject.getTimeComplexity());
                updateAlgorithm.setSpaceComplexity(algorithmObject.getSpaceComplexity());
                updateAlgorithm.setConstraints(algorithmObject.getConstraints());
                updateAlgorithm.setPublic(algorithmObject.isPublic());
                return algorithmRepository.save(algorithmObject);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    public void deleteAlgorithm(Long categoryId, Long algorithmId) {
        System.out.println("service calling deleteCategoryRecipe ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder .getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            Algorithm algorithm = (algorithmRepository.findByCategoryId(
                    categoryId).stream().filter(p -> p.getId().equals(algorithmId)).findFirst()).get();
            algorithmRepository.deleteById(algorithm.getId());
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("recipe or category not found");
        }
    }
}
