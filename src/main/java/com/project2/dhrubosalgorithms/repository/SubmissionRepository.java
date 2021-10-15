package com.project2.dhrubosalgorithms.repository;

import com.project2.dhrubosalgorithms.model.Submissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submissions, Long> {

    Submissions findByUserIdAndAlgorithm_Id(Long userId, Long algorithmId);

    List<Submissions> findByUserId(Long userId);
    List<Submissions> findByAlgorithm_Id(Long algorithmId);
    List<Submissions> findByStatusIs(String status);
    List<Submissions> findByPass(Boolean submissionPass);

}
