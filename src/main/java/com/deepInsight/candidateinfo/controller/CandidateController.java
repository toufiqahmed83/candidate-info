package com.deepInsight.candidateinfo.controller;

import com.deepInsight.candidateinfo.model.CandidateInfo;
import com.deepInsight.candidateinfo.service.CandideteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CandidateController {

    @Autowired
    private CandideteService candideteService;

    @GetMapping("/All")
    public String getAllCandidate(Model model)
    {
        List<CandidateInfo> candidateInfos = this.candideteService.findAll();
        model.addAttribute("candidateInfos",candidateInfos);
//        return "index";
        return "info";
    }

    @GetMapping("/Create")
    public String createCandidate(Model model)
    {
        CandidateInfo candidateInfo = new CandidateInfo();
        model.addAttribute("candidateInfo",candidateInfo);
        return "creat";
    }

    @PostMapping("/Create")
    public String createCandidatePost(@Validated CandidateInfo candidateInfo, Model model)
    {
        this.candideteService.save(candidateInfo);
//        model.addAttribute("candidateInfo",candidateInfo);
        return "redirect:/All";
    }

    @GetMapping("/Edit/{id}")
    public String createCandidate(@PathVariable("id") Integer id, Model model)
    {
        CandidateInfo candidateInfo = this.candideteService.findByid(id);
        model.addAttribute("candidateInfo",candidateInfo);
        return "creat";
    }



    @GetMapping("/Delete/{id}")
    public String deleteCandidate(@PathVariable("id") Integer id, Model model)
    {
        CandidateInfo candidateInfo = this.candideteService.findByid(id);
        this.candideteService.delete(candidateInfo);
//        model.addAttribute("candidateInfo",candidateInfo);
        return "redirect:/All";
    }

}
