package com.project2.dhrubosalgorithms.repository;

import com.project2.dhrubosalgorithms.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    Algorithm findByName(String algorithmName);
    Algorithm findByNameAndIdIsNot(String algorithmName, Long algorithmId);

    List<Algorithm> findByCategoryId(Long algorithmId);
}
