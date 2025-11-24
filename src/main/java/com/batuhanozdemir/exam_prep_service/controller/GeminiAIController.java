package com.batuhanozdemir.exam_prep_service.controller;

import com.batuhanozdemir.exam_prep_service.entity.ChatEntity;
import com.batuhanozdemir.exam_prep_service.entity.QuestionAnswerPOJO;
import com.batuhanozdemir.exam_prep_service.entity.Users;
import com.batuhanozdemir.exam_prep_service.service.ChatService;
import com.batuhanozdemir.exam_prep_service.service.GeminiResponseService;
import com.batuhanozdemir.exam_prep_service.service.NewUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class GeminiAIController {

    private final GeminiResponseService geminiResponseService;

    private final ChatService chatService;

    private final NewUserService newUserService;

    @PostMapping("/ask")
    public ResponseEntity<ChatEntity> askQuestion(@RequestBody Map<String , String> payload){

        try{
            //get Question
            String question = payload.get("question");
            String userEmail = payload.get("userEmail");

            Users user = newUserService.findByUserEmail(userEmail);
            if(user != null) {
                //Getting Answer
                QuestionAnswerPOJO answer = geminiResponseService.getAnswer(question);
                ChatEntity chat = new ChatEntity();

                if (question != null && answer != null) {

                    chat.setQuestion(question);
                    chat.setResponse(answer.candidates.get(0).content.parts.get(0).text);
                    chatService.saveData(chat, userEmail);
                }
                //Return the answer
                return new ResponseEntity<>(chat, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
}

