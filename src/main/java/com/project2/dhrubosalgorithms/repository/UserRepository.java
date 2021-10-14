package com.project2.dhrubosalgorithms.repository;

import com.project2.dhrubosalgorithms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // register
    boolean existsByEmailAddress(String userEmailAddress);

    // login
    User findUserByEmailAddress(String userEmailAddress);
}