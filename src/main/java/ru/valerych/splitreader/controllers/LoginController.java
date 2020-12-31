package ru.valerych.splitreader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.dto.UserLoginDTO;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping(value = "api/v1/login", consumes = "application/json", produces = "application/json")
    public void login(@RequestBody UserLoginDTO userLoginDTO) {
        userService.auth(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        User user = (User) session.getAttribute("user");
        System.out.println(user.getUsername());
    }

    @GetMapping(value = "api/v1/csrf-token")
    public @ResponseBody
    CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
