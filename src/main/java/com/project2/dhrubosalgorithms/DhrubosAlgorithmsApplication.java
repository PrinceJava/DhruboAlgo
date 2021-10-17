package com.project2.dhrubosalgorithms;


import com.project2.dhrubosalgorithms.model.Algorithm;
import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
import com.project2.dhrubosalgorithms.repository.CategoryRepository;
import com.project2.dhrubosalgorithms.repository.UserRepository;
import com.project2.dhrubosalgorithms.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class DhrubosAlgorithmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhrubosAlgorithmsApplication.class, args);
    }
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder = passwordEncoder;}
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository;}
    private CategoryRepository categoryRepository;
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){this.categoryRepository = categoryRepository;}

    @Bean
    CommandLineRunner run(InitService initService){
        return args -> {

            initService.addRole(new Role(null,"ROLE_ADMIN"));
            initService.addRole(new Role(null,"ROLE_USER"));


            initService.addUser(new User(null,"admin","admin@admin.com",passwordEncoder.encode("admin")));
            initService.addUser(new User(null,"user1","user1@paypal.com",passwordEncoder.encode("user1")));
            initService.addUser(new User(null,"user2","user2@paypal.com",passwordEncoder.encode("user2")));
            initService.addUser(new User(null,"user3","user3@paypal.com",passwordEncoder.encode("user3")));
            initService.addUser(new User(null,"matjames","matjames@paypal.com",passwordEncoder.encode("matjames")));

            initService.addUserRole("admin","ROLE_ADMIN");
            initService.addUserRole("admin","ROLE_USER");
            initService.addUserRole("user1","ROLE_USER");
            initService.addUserRole("user2","ROLE_USER");
            initService.addUserRole("user3","ROLE_USER");
            initService.addUserRole("matjames","ROLE_USER");

            initService.createCategory(new Category(null,"arrays","Algorithms based off Array Data Structure", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"string","Algorithms including creating and manipulation of Strings", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"hash_table","Algorithms based off Hash Tables", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"tree","Algorithms utilizing tree data structure", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"sorting","Algorithms that sort data structures", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"binary_search","Binary Search Algorithms", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"database","SQL and Database algorithms", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"backtracking","Backtracking Algorithms", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"hash_map","Algorithms based off Hash Maps", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"dynamic_programming","Dynamic Programming Algorithms", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"binary_tree","Algorithms based off Binary Tree Search", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"depth_first_search","Algorithms based off Depth-First-Search", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"matrix","Matrix and Matrix Traversal algorithms", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"stack","Stack manipulation Algorithms", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"linked_list","Algorithms based off Linked-List", null, userRepository.findUserByUserName("admin") ));
            initService.createCategory(new Category(null,"recursion","Algorithms based off Algorithms based off Algorithms", null, userRepository.findUserByUserName("admin") ));

            initService.createAlgorithm(new Algorithm(
                    null,
                    "two_sum",
                    "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.",
                    "easy",
                    "A really brute force way would be to search for all possible pairs of numbers but that would be too slow.",
                    "can you make it less than 0(n^2)?",
                    "0(n+1)",
                    "2 <= nums.length <= 104\n" +
                    "-109 <= nums[i] <= 109\n" +
                    "-109 <= target <= 109\n" +
                    "Only one valid answer exists.",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("arrays"),
                    null));
        };
    }
}
