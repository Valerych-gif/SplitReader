package ru.valerych.splitreader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.dto.UserLoginDTO;
import ru.valerych.splitreader.services.UserService;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "api/v1/login", consumes = "application/json", produces = "application/json")
    public void login(@RequestBody UserLoginDTO userLoginDTO){
        UserDTO user = userService.getUserByUsernameAndPass(userLoginDTO.getEmail(), userLoginDTO.getPassword());
    }
}
