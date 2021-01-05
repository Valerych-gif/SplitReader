package ru.valerych.splitreader.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.dto.UserLoginDTO;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session;

    @GetMapping(value = "api/v1/user")
    public @ResponseBody
    UserDetails getUserDetails(HttpServletRequest request) {
        return (UserDetails) session.getAttribute("userDetails");
    }

    @GetMapping(value = "api/v1/csrf-token")
    public @ResponseBody
    CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
