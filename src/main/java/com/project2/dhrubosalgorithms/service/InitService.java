package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.repository.AlgorithmRepository;
import com.project2.dhrubosalgorithms.repository.CategoryRepository;
import com.project2.dhrubosalgorithms.repository.RoleRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- INIT SERVICES ---------

INIT SERVICES is responsible for setting all values called from BEAN CommandLineRunner on DhruboAlgorithmsApplication.java file

Goal of this page is to
1. create a list of roles and add to Roles TABLE
2. create a list of users and add to User TABLE
    A. Utilize passwordEncoder.encode() to store init data encrypted
3. create a list of Categories and add to Category TABLE
4. create a list of Algorithms and assign them to a Category (findByName: admin) and add to Algorithms TABLE
 */
@Service
@Transactional
public class InitService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    CategoryRepository categoryRepository;
    AlgorithmRepository algorithmRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setAlgorithmRepository(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public User addUser(User userObject) {
        System.out.println("Calling INIT SERVICE saveUser ==>");
        return userRepository.save(userObject);
    }

    public Role addRole(Role roleObject) {
        System.out.println("Calling INIT SERVICE saveRole ==>");
        return roleRepository.save(roleObject);
    }

    // METHOD IS A VOID, SO I DON'T NEED TO RETURN ANYTHING OR SAVE WITH TRANSACTIONAL
    public void addUserRole(String username, String roleName) {
        System.out.println("Calling INIT SERVICE addRoleToUser ==>");
        User user = userRepository.findUserByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        user.setRoles(user.getRoles());
    }

    // METHOD RETURNS A CATEGORY AND SAVES IT TO THE CATEGORY TABLE VIA categoryRepository
    public Category createCategory(Category categoryObject) {
        System.out.println("Calling INIT SERVICE createCategory ==>");
        return categoryRepository.save(categoryObject);
    }

    // METHOD RETURNS AN ALGORITHM AND SAVES IT TO THE ALGORITHM TABLE VIA algorithmRepository
    public Algorithm createAlgorithm(Algorithm algorithmObject) {
        System.out.println("Calling INIT SERVICE createAlgorithm ==>");
        return algorithmRepository.save(algorithmObject);
    }
}
