package com.batuhanozdemir.exam_prep_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExamPrepServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamPrepServiceApplication.class, args);
	}

}
