package ru.valerych.splitreader.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.services.UserServiceImpl;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public UserDTO getUserDetails() {
        // TODO Update last visit date
        return userService.getCurrentUserDTO();
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        userService.updateAuthUserDTO(userDTO);
        return userService.getCurrentUserDTO();
    }
}
