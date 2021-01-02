package ru.valerych.splitreader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session;

    @PostMapping(value = "api/v1/login", consumes = "application/json", produces = "application/json")
    public void login(@RequestBody UserLoginDTO userLoginDTO) {
        UserDetails user = userService.loadUserByUsername(userLoginDTO.getUsername());
    }

    @GetMapping(value = "api/v1/csrf-token")
    public @ResponseBody
    CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
