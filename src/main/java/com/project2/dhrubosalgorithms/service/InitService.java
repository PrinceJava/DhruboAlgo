package com.project2.dhrubosalgorithms.service;

import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.repository.CategoryRepository;
import com.project2.dhrubosalgorithms.repository.RoleRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import com.project2.dhrubosalgorithms.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InitService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    CategoryRepository categoryRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){this.categoryRepository = categoryRepository;}


    public User addUser(User userObject) {
        System.out.println("Calling AdminService saveUser ==>");
        return userRepository.save(userObject);
    }

    public Role addRole(Role roleObject) {
        System.out.println("Calling AdminService saveRole ==>");
        return roleRepository.save(roleObject);
    }

    // METHOD IS A VOID, SO I DON'T NEED TO RETURN ANYTHING OR SAVE WITH TRANSACTIONAL
    public void addUserRole(String username, String roleName) {
        System.out.println("ADMIN SERVICE - addRoleToUser ==>");
        User user = userRepository.findUserByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        user.setRoles(user.getRoles());
    }

    public Category createCategory(Category categoryObject) {
        System.out.println("Calling CategoryService createCategory ==>");
        return categoryRepository.save(categoryObject);
    }
}
