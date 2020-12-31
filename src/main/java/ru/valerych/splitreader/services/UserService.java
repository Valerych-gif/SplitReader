package ru.valerych.splitreader.services;

import org.springframework.stereotype.Service;
import ru.valerych.splitreader.dto.UserDTO;

@Service
public class UserService {

    public UserDTO getUserByUsernameAndPass(String email, String password) {
        if (email.equals("user@mail.com")&&password.equals("1234")){
            return new UserDTO(email, password);
        }
        return null;
    }
}
