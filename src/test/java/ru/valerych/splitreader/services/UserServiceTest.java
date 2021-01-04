package ru.valerych.splitreader.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.repositories.UserRepository;

import java.util.Collections;

import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
        when(userRepository.findUserByUserName("user@mail.com"))
                .thenReturn(new User(
                        "user@mail.com",
                        "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
                        Collections.singletonList(new Role("USER"))
                ));
        when(userRepository.findUserByUserName("admin@mail.com"))
                .thenReturn(new User(
                        "admin@mail.com",
                        "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
                        Collections.singletonList(new Role("ADMIN"))
                ));
    }

    @Test
    @DisplayName("User login is success")
    void loadUserByUsernameSuccessTest() {
        UserDetails user = userService.loadUserByUsername("user@mail.com");
        Assertions.assertEquals("$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K", user.getPassword());
    }

    @Test
    @DisplayName("User login is failed")
    void loadUserByUsernameFailTest() {
        Assertions.assertThrows(UsernameNotFoundException.class, ()->userService.loadUserByUsername("baduser@mail.com"));
    }
}