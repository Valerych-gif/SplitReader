package ru.valerych.splitreader.services.contracts;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.entities.User;

public interface UserService extends UserDetailsService{

    User getCurrentUser();
    User getUserByUsername(String username);
    User createUser(UserDTO userDTO);
    boolean updateUserDTO(UserDTO userDTO);
    UserDTO getCurrentUserDTO();

}
