package ru.valerych.splitreader.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public UserDTO getUserDetails() {
        User user = userService.getAuthUser();
        if (user==null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword("******");
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRoles(user.getRoles());
        userDTO.setCity(userDTO.getCity());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setLastVisit(user.getLastVisit());
        userDTO.setCurrentBookId(user.getCurrentBookId());
        return userDTO;
    }
}
