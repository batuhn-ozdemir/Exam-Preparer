package com.batuhanozdemir.exam_prep_service.controller;

import com.batuhanozdemir.exam_prep_service.entity.MessageResponseFromScript;
import com.batuhanozdemir.exam_prep_service.entity.QuestionAnswerPOJO;
import com.batuhanozdemir.exam_prep_service.entity.QuestionPOJO;
import com.batuhanozdemir.exam_prep_service.service.GeminiResponseService;
import com.batuhanozdemir.exam_prep_service.service.ScriptFormCreationService;
import com.batuhanozdemir.exam_prep_service.service.StringToQuestionPOJOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:5173")
public class FormCreationController {

    @Autowired
    public ScriptFormCreationService formCreationService;
    @Autowired
    public GeminiResponseService geminiResponseService;
    @Autowired
    public StringToQuestionPOJOService pojoConverterService;

    @PostMapping
    public ResponseEntity<MessageResponseFromScript> createFormL(@RequestBody Map<String, String> payload) {
        //Ask Ai for the questions
        String getTheQuestions = "";
        QuestionPOJO questionPOJO;

        try {
            //get Question
            String question = payload.get("question");
            //Getting Answer
            QuestionAnswerPOJO answer = geminiResponseService.getAnswer(question, 25);

            getTheQuestions = answer.candidates.get(0).content.parts.get(0).text; //get as a string

            String data = getTheQuestions.replace("```json", "").replace("```", "")
                    .replace("\n", "")  // Remove newline characters if needed
                    .trim();  // Remove leading or trailing spaces

            questionPOJO = pojoConverterService.convertJsonToPojo(data);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Now Create Form using the questions you receive
        try {

            MessageResponseFromScript response = formCreationService.getFormURL(questionPOJO);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

