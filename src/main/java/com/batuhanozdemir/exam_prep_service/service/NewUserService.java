package com.batuhanozdemir.exam_prep_service.service;

import com.batuhanozdemir.exam_prep_service.entity.Users;
import com.batuhanozdemir.exam_prep_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class NewUserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewEntity(Users user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){

            log.info("Error occur while creating user "+e);
            return false;
        }

    }
    public void saveEntity(Users user){
        userRepository.save(user);
    }

    public List<Users> getAll(){
        return userRepository.findAll();
    }

    public Users findByUserEmail(String email){
        return userRepository.findByUserEmail(email);
    }

}

