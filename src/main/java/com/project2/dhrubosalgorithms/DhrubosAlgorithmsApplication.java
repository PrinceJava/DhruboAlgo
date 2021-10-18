package com.project2.dhrubosalgorithms;

/*
DHRUBOS ALGORITHM REST API PROJECT
------- APPLICATION PAGE ---------

Active Profile running is Application-DEV;
Database Settings
    server.port=9999
    spring.jpa.hibernate.ddl-auto=create
    spring.datasource.url=jdbc:postgresql://localhost:5432/algorithm
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.jpa.show-sql=true
    server.error.include-stacktrace=ALWAYS

Goal of this page is to
1. initialize the Maven dependencies
2. Run initialization script to preload database tables
 */

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

    /**
     * This CommandLineRunner will pull in the InitService class and utilize the methods to fill the database.
     * Sections included are
     *      Create Roles, Create Users, Add Role To User,
     *      Create Category, Create Algorithm
     *
     * @param initService - will pull all the init service methods, purpose of this method is they do not require JWT authorization
     */
    @Bean
    CommandLineRunner run(InitService initService){
        return args -> {

            initService.addRole(new Role(null,"ROLE_ADMIN"));
            initService.addRole(new Role(null,"ROLE_USER"));


            initService.addUser(new User(null,"admin","admin@admin.com",passwordEncoder.encode("admin")));
            initService.addUser(new User(null,"user1","user1@user.com",passwordEncoder.encode("user1")));
            initService.addUser(new User(null,"user2","user2@user2.com",passwordEncoder.encode("user2")));
            initService.addUser(new User(null,"user3","user3@user3.com",passwordEncoder.encode("user3")));
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
            initService.createAlgorithm(new Algorithm(
                    null,
                    "zig_zag_conversion",
                    "Write the code that will take a string and make this conversion given a number of rows:",
                    "medium",
                    null,
                    "can you make it less than 0(n^2)?",
                    "0(n+1)",
                    "1 <= s.length <= 1000\n" +
                            "1 <= numRows <= 1000",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("string"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "letter_combinations_of_a_phone_number",
                    "Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.\n",
                    "medium",
                    null,
                    "can you make it less than 0(n^2)?",
                    "0(n+1)",
                    "0 <= digits.length <= 4\n" +
                            "digits[i] is a digit in the range ['2', '9'].",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("hash_table"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "binary_tree_inorder_traversal",
                    "Given the root of a binary tree, return the inorder traversal of its nodes' values.",
                    "easy",
                    null,
                    "can you make it less than 0(n^2)?",
                    "Recursive solution is trivial, could you do it iteratively?",
                    "The number of nodes in the tree is in the range [0, 100].\n" +
                            "-100 <= Node.val <= 100",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("tree"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "binary_tree_inorder_traversal",
                    "Given the root of a binary tree, return its maximum depth.\n" +
                            "\n" +
                            "A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.",
                    "easy",
                    null,
                    null,
                    null,
                    "The number of nodes in the tree is in the range [0, 104].\n" +
                            "-100 <= Node.val <= 100",
                    false,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("tree"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "4sum",
                    "Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] \n" +
                    " You may return the answer in any order.",
                    "medium",
                    null,
                    "Can you make time complexity less than O(n^2)?",
                    "O(n+1)",
                    "1 <= nums.length <= 200\n" +
                            "-109 <= nums[i] <= 109\n" +
                            "-109 <= target <= 109",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("sorting"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "median_of_two_sorted_arrays",
                    "Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.",
                    "medium",
                    null,
                    "The overall run time complexity should be O(log (m+n))",
                    "O(n+1)",
                    "nums1.length == m\n" +
                            "nums2.length == n\n" +
                            "0 <= m <= 1000\n" +
                            "0 <= n <= 1000\n" +
                            "1 <= m + n <= 2000\n" +
                            "-106 <= nums1[i], nums2[i] <= 106",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("binary_search"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "binary_tree_cousins",
                    "Given the root of a binary tree with unique values and the values of two different nodes of the tree x and y, return true if the nodes corresponding to the values x and y in the tree are cousins, or false otherwise.",
                    "easy",
                    "Note that in a binary tree, the root node is at the depth 0, and children of each depth k node are at the depth k + 1.",
                    "The overall run time complexity should be O(log (m+n))",
                    "O(n+1)",
                    "The number of nodes in the tree is in the range [2, 100].\n" +
                            "1 <= Node.val <= 100\n" +
                            "Each node has a unique value.\n" +
                            "x != y\n" +
                            "x and y are exist in the tree.",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("database"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "sudoku_solver",
                    "Write a program to solve a Sudoku puzzle by filling the empty cells.",
                    "hard",
                    "The '.' character indicates empty cells.",
                    null,
                    null,
                    "board.length == 9\n" +
                            "board[i].length == 9\n" +
                            "board[i][j] is a digit or '.'.\n" +
                            "It is guaranteed that the input board has only one solution.",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("backtracking"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "roman_to_integer",
                    "Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.",
                    "easy",
                    "Problem is simpler to solve by working the string from back to front and using a map.\n" +
                            "\n",
                    null,
                    null,
                    "1 <= s.length <= 15\n" +
                            "s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').\n" +
                            "It is guaranteed that s is a valid roman numeral in the range [1, 3999].",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("hash_map"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "longest_palindromic_substring",
                    "Given a string s, return the longest palindromic substring in s.",
                    "medium",
                    "How can we reuse a previously computed palindrome to compute a larger palindrome?\n",
                    "Can we reduce the time for palindromic checks to O(1) by reusing some previous computation.",
                    "Less than O(n^2)",
                    "1 <= s.length <= 15\n" +
                            "s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').\n" +
                            "It is guaranteed that s is a valid roman numeral in the range [1, 3999].",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("dynamic_programming"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "same_tree",
                    "Given the roots of two binary trees p and q, write a function to check if they are the same or not.",
                    "easy",
                    "Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.",
                    null,
                    null,
                    "The number of nodes in both trees is in the range [0, 100].\n" +
                            "-104 <= Node.val <= 104",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("binary_tree"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "flatten_binary_tree_to_linked_list",
                    "Given the root of a binary tree, flatten the tree into a \"linked list\":",
                    "medium",
                    "If you notice carefully in the flattened tree, each node's right child points to the next node of a pre-order traversal.\n" +
                            "\n",
                    null,
                    null,
                    "The number of nodes in the tree is in the range [0, 2000].\n" +
                            "-100 <= Node.val <= 100",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("depth_first_search"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "rotate_image",
                    "You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).",
                    "medium",
                    "If you notice carefully in the flattened tree, each node's right child points to the next node of a pre-order traversal.\n" +
                            "\n",
                    null,
                    "Do not create another 2D array matrix",
                    "matrix.length == n\n" +
                            "matrix[i].length == n\n" +
                            "1 <= n <= 20\n" +
                            "-1000 <= matrix[i][j] <= 1000",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("matrix"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "valid_parentheses",
                    "Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.",
                    "easy",
                    "An interesting property about a valid parenthesis expression is that a sub-expression of a valid expression should also be a valid expression.",
                    null,
                    "Do not create another 2D array matrix",
                    "1 <= s.length <= 104\n" +
                            "s consists of parentheses only '()[]{}'.",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("stack"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "remove_nth_node_from_end_of_list",
                    "Given the head of a linked list, remove the nth node from the end of the list and return its head.\n" +
                            "\n",
                    "medium",
                    "An interesting property about a valid parenthesis expression is that a sub-expression of a valid expression should also be a valid expression.",
                    "Could you do this in one pass?",
                    null,
                    "The number of nodes in the list is sz.\n" +
                            "1 <= sz <= 30\n" +
                            "0 <= Node.val <= 100\n" +
                            "1 <= n <= sz",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("linked_list"),
                    null));
            initService.createAlgorithm(new Algorithm(
                    null,
                    "merge_two_sorted_lists",
                    "Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together the nodes of the first two lists.",
                    "easy",
                    "An interesting property about a valid parenthesis expression is that a sub-expression of a valid expression should also be a valid expression.",
                    null,
                    "null",
                    "The number of nodes in both lists is in the range [0, 50].\n" +
                            "-100 <= Node.val <= 100\n" +
                            "Both l1 and l2 are sorted in non-decreasing order.",
                    true,
                    userRepository.findUserByUserName("admin"),
                    categoryRepository.findByName("recursion"),
                    null));
        };
    }
}
