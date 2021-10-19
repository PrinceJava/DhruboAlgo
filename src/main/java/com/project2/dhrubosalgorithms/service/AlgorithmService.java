package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Category;
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
------- ALGORITHM SERVICES ---------

Main purpose of this Service is to perform CRUD functions on ALGORITHM TABLE via algorithmRepository

Goal of this page is to
1. POST to algorithmRepository via createCategory taking in JSON Category Object
2. GET All Algorithms utilizing algorithmRepository.findAll()
3. GET AN Algorithm based on Path Variable Category ID
4. UPDATE AN Algorithm based on passed Object and Path Variable
5. DELETE AN Algorithm based on passed Path Variable
 */

@Service
@Transactional
public class AlgorithmService {
    private CategoryRepository categoryRepository;
    private AlgorithmRepository algorithmRepository;


    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setAlgorithmRepository(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }


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
        try {
            Category category = categoryRepository.findByName(categoryName);
            algorithmObject.setCategory(category);
            return algorithmRepository.save(algorithmObject);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("category with name - " + categoryName + " not found");
        }
    }

    public Algorithm getAlgorithm(Long categoryId, Long recipeId) {
        System.out.println("service calling getCategoryRecipe ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
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
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        try {
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
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
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
