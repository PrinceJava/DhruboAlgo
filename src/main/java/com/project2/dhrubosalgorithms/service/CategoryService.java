package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.exception.InformationExistException;
import com.project2.dhrubosalgorithms.exception.InformationNotFoundException;
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
------- CATEGORY SERVICES ---------

Main purpose of this Service is to perform CRUD functions on CATEGORY TABLE via categoryRepository

Goal of this page is to
1. POST to categoryRepository via createCategory taking in JSON Category Object
2. GET All Categories utilizing categoryRepository.findAll()
3. GET A Category based on Path Variable Category ID
4. UPDATE A Category based on passed Object and Path Variable
5. DELETE A Category based on passed Path Variable
 */

@Service
@Transactional
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category createCategory(Category categoryObject) {
        System.out.println("Calling CategoryService createCategory ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return categoryRepository.save(categoryObject);
    }

    // get all categories
    public List<Category> getCategories() {
        System.out.println("service calling getCategories ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        try {
            return categoryRepository.findAll();
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("No Categories were found in the database");
        }
    }

    // get category
    public Optional<Category> getCategory(Long categoryId) {
        System.out.println("service getCategory ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return category;
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }


    // put categories
    public Category updateCategory(Long categoryId, Category categoryObject) {
        System.out.println("service calling updateCategory ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            if (categoryObject.getName().equals(category.get().getName())) {
                throw new InformationExistException("category " + category.get().getName() + " is already exists");
            } else {
                Category updateCategory = categoryRepository.findById(categoryId).get();
                updateCategory.setName(categoryObject.getName());
                updateCategory.setDescription(categoryObject.getDescription());
                return categoryRepository.save(updateCategory);
            }
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    // delete categories
    public void deleteCategory(Long categoryId) {
        System.out.println("service calling deleteCategory ==>");
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
}
