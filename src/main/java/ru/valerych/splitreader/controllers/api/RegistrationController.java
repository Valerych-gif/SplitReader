package ru.valerych.splitreader.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.responses.RegResponse;
import ru.valerych.splitreader.services.RoleServiceImpl;
import ru.valerych.splitreader.services.UserServiceImpl;

@Controller
@RequestMapping("api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;


    @PostMapping(consumes = "application/json")
    public ResponseEntity<RegResponse> setUserDetails(@RequestBody UserDTO userDTO) {

        if (userDTO.getUsername()==null||userDTO.getPassword()==null){
            System.out.println("Username or password is empty");
            return ResponseEntity.badRequest().build();
        }
        if (userService.getUserByUsername(userDTO.getUsername())!=null){
            System.out.println("Username is busy");
            return ResponseEntity.badRequest().build();
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            System.out.println("Passwords are not equality");
            return ResponseEntity.badRequest().build();
        }
        if (!userDTO.isAcceptRules()){
            System.out.println("You must accept our rules");
            return ResponseEntity.badRequest().build();
        }

        userService.createUser(userDTO);
        return ResponseEntity.ok().build();
    }
}
