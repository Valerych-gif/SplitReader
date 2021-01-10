package ru.valerych.splitreader.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.valerych.splitreader.dto.RegUserDTO;
import ru.valerych.splitreader.responses.RegResponse;
import ru.valerych.splitreader.services.RoleService;
import ru.valerych.splitreader.services.UserService;

@Controller
@RequestMapping("api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @PostMapping(consumes = "application/json")
    public ResponseEntity<RegResponse> setUserDetails(@RequestBody RegUserDTO regUserDTO) {

        if (regUserDTO.getUsername()==null||regUserDTO.getPassword()==null){
            System.out.println("Username or password is empty");
            return ResponseEntity.badRequest().build();
        }
        if (userService.getUserByUsername(regUserDTO.getUsername())!=null){
            System.out.println("Username is busy");
            return ResponseEntity.badRequest().build();
        }
        if (!regUserDTO.getPassword().equals(regUserDTO.getConfirmPassword())){
            System.out.println("Passwords are not equality");
            return ResponseEntity.badRequest().build();
        }
        if (!regUserDTO.isAcceptRules()){
            System.out.println("You must accept our rules");
            return ResponseEntity.badRequest().build();
        }

        userService.createUser(regUserDTO);
        return ResponseEntity.ok().build();
    }
}
