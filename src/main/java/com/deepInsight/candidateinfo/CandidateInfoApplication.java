package com.deepInsight.candidateinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@AutoConfiguration
@EntityScan("com.deepinsight")
public class CandidateInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidateInfoApplication.class, args);
	}

}
