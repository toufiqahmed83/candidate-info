package com.deepInsight.candidateinfo.controller;

import com.deepInsight.candidateinfo.config.SuperSetAuth;
import com.deepInsight.candidateinfo.config.SuperSetAuth2;
import com.deepInsight.candidateinfo.config.SupersetGuestTokenService;
import com.deepInsight.candidateinfo.model.CandidateInfo;
import com.deepInsight.candidateinfo.service.CandideteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CandidateController {

    @Autowired
    private CandideteService candideteService;

    @Autowired
    private SuperSetAuth2 superSetAuth;

    @GetMapping("/All")
    public String getAllCandidate(Model model) {
        List<CandidateInfo> candidateInfos = this.candideteService.findAll();
        model.addAttribute("candidateInfos", candidateInfos);
        // try {
        // String loginToSuperset = superSetAuth.loginToSuperset();
        // System.out.println(loginToSuperset);
        // model.addAttribute("dashBoardUrl",loginToSuperset);
        // } catch (JsonProcessingException e) {
        // throw new RuntimeException(e);
        // }
        // return "index";
        return "info";
    }

    @GetMapping("/Create")
    public String createCandidate(Model model) {
        CandidateInfo candidateInfo = new CandidateInfo();
        model.addAttribute("candidateInfo", candidateInfo);
        return "creat";
    }

    @PostMapping("/SaveCandidate")
    // public String createCandidatePost(@Validated CandidateInfo candidateInfo,
    // Model model)
    public String createCandidatePost(@ModelAttribute("candidateInfo") CandidateInfo candidateInfo, Model model) {
        this.candideteService.save(candidateInfo);
        // model.addAttribute("candidateInfo",candidateInfo);
        return "redirect:/All";
    }

    @GetMapping("/Edit/{id}")
    public String createCandidate(@PathVariable("id") Integer id, Model model) {
        CandidateInfo candidateInfo = this.candideteService.findByid(id);
        model.addAttribute("candidateInfo", candidateInfo);
        return "creat";
    }

    @GetMapping("/Delete/{id}")
    public String deleteCandidate(@PathVariable("id") Integer id, Model model) {
        CandidateInfo candidateInfo = this.candideteService.findByid(id);
        this.candideteService.delete(candidateInfo);
        // model.addAttribute("candidateInfo",candidateInfo);
        return "redirect:/All";
    }

    @GetMapping("/api/v1/superset/token")
    public ResponseEntity<?> getJwt() {
        // SupersetGuestTokenService service = new SupersetGuestTokenService();

        // return ResponseEntity.ok(service.generateGuestToken());
        try {
            // String token = service.generateGuestToken();
            String token=superSetAuth.loginToSuperset();
            System.out.println("Generated Token: " + token); // Debugging
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Token generation failed\"}");
        }
    }

}
