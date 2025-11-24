package com.batuhanozdemir.exam_prep_service.service;

import com.batuhanozdemir.exam_prep_service.entity.ChatEntity;
import com.batuhanozdemir.exam_prep_service.entity.Users;
import com.batuhanozdemir.exam_prep_service.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private NewUserService userService;


    @Transactional
    public void saveData(ChatEntity chatEntity ,  String userEmail){

        try {

            Users users = userService.findByUserEmail(userEmail);
            ChatEntity chat = chatRepository.save(chatEntity);
            users.getChatEntities().add(chat);
            userService.saveEntity(users);

        }
        catch (Exception e){

            throw new RuntimeException(e);

        }

    }

}

