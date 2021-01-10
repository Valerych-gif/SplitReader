package ru.valerych.splitreader.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.services.UserService;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public UserDTO getUserDetails() {
        return userService.getAuthUserDTO();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        userService.updateAuthUserDTO(userDTO);
        return userService.getAuthUserDTO();
    }
}
