package com.project2.dhrubosalgorithms;


import com.project2.dhrubosalgorithms.model.Category;
import com.project2.dhrubosalgorithms.model.Role;
import com.project2.dhrubosalgorithms.model.User;
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

            initService.createCategory(new Category(null,"Arrays","Algorithms based off Array Data Structure", null, userRepository.findUserByUserName("admin") ));
//            this.id = id;
//            this.name = name;
//            this.description = description;
//            this.algorithms = algorithms;
//            this.user = user;
        };
    }
}
