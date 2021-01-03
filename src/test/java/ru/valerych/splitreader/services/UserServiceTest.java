package ru.valerych.splitreader.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.repositories.UserRepository;

import java.util.Collections;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
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
    void loadUserByUsername() {
        UserDetails user = userService.loadUserByUsername("user@mail.com");
        Assertions.assertEquals("$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K", user.getPassword());
    }
}