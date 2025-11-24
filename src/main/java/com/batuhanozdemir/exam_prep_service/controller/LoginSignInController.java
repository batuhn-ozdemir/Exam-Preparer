package com.batuhanozdemir.exam_prep_service.controller;

import com.batuhanozdemir.exam_prep_service.entity.Users;
import com.batuhanozdemir.exam_prep_service.service.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/req")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginSignInController {

    @Autowired
    private NewUserService userService;

    private final PasswordEncoder passwdEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<Users> createUser(@RequestBody Users users){

        if(userService.findByUserEmail(users.getUserEmail()) != null){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        if(userService.saveNewEntity(users)){

            return new ResponseEntity<>(users , HttpStatus.OK);

        }
        else {

            return new ResponseEntity<>(users , HttpStatus.NO_CONTENT);

        }

    }
    @PostMapping("/login")
    public ResponseEntity<Users> loginUser(@RequestBody Map<String , String> payload){

        Users users1 = userService.findByUserEmail(payload.get("userEmail"));

        if(users1 != null) {
            if (passwdEncoder.matches(payload.get("password") , users1.getPassword())) {

                return new ResponseEntity<>(users1 , HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/txt")
    public String connected(){
        return "Connected User";
    }

}

