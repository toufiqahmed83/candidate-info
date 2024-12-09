package com.deepInsight.candidateinfo.service;

import com.deepInsight.candidateinfo.model.CandidateInfo;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.List;

public interface CandideteService {

    List<CandidateInfo> findAll();

    CandidateInfo findByid(int id);

    void save(CandidateInfo candidateInfo);

    void delete(CandidateInfo candidateInfo);
}
