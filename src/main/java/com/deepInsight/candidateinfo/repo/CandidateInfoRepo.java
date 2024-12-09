package com.deepInsight.candidateinfo.repo;

import com.deepInsight.candidateinfo.model.CandidateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateInfoRepo extends JpaRepository<CandidateInfo,Integer> {
}
