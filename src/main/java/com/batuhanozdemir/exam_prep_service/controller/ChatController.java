package com.batuhanozdemir.exam_prep_service.controller;

import com.batuhanozdemir.exam_prep_service.entity.ChatEntity;
import com.batuhanozdemir.exam_prep_service.entity.Users;
import com.batuhanozdemir.exam_prep_service.service.ChatService;
import com.batuhanozdemir.exam_prep_service.service.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private NewUserService userService;

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<ChatEntity>> getAllChats(@PathVariable String userEmail) {

        Users user = userService.findByUserEmail(userEmail);
        List<ChatEntity> allChats = user.getChatEntities();

        if (allChats != null) {

            return new ResponseEntity<>(allChats, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}

