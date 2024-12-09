package com.deepInsight.candidateinfo.service;

import com.deepInsight.candidateinfo.model.CandidateInfo;
import com.deepInsight.candidateinfo.repo.CandidateInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandideteServiceImpl implements CandideteService {

    @Autowired
    private CandidateInfoRepo candidateInfoRepo;

    @Override
    public List<CandidateInfo> findAll() {
        return this.candidateInfoRepo.findAll();
    }

    @Override
    public CandidateInfo findByid(int id) {
        return this.candidateInfoRepo.findById(id).orElseThrow(()-> new RuntimeException("Invalid id"));
    }

    @Override
    public void save(CandidateInfo candidateInfo) {
        this.candidateInfoRepo.save(candidateInfo);
    }

    @Override
    public void delete(CandidateInfo candidateInfo) {
        this.candidateInfoRepo.delete(candidateInfo);
    }
}
