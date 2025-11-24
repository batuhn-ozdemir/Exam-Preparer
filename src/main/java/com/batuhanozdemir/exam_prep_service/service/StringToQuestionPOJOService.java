package com.batuhanozdemir.exam_prep_service.service;

import com.batuhanozdemir.exam_prep_service.entity.QuestionPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class StringToQuestionPOJOService {

    public QuestionPOJO convertJsonToPojo(String jsonString) throws JsonProcessingException {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert JSON string to QuestionPOJO
        return objectMapper.readValue(jsonString, QuestionPOJO.class);
    }

}
