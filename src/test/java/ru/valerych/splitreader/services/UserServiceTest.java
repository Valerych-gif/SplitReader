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
import java.util.Date;

import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
        when(userRepository.findUserByUsername("user@mail.com"))
                .thenReturn(
                        new User(
                                1L,
                                "user@mail.com",
                                "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
                                Collections.singletonList(new Role(0L, "USER")),
                                true,
                                true,
                                true,
                                true,
                                "Ivan",
                                "Ivanov",
                                null,
                                "Moscow",
//                                new ArrayList<Genre>(),
                                new Date(),
                                1L
//                                new ArrayList<Book>()
                        ));
        when(userRepository.findUserByUsername("admin@mail.com"))
                .thenReturn(
                        new User(
                                2L,
                                "admin@mail.com",
                                "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
                                Collections.singletonList(new Role(1L, "ADMIN")),
                                true,
                                true,
                                true,
                                true,
                                "Петр",
                                "Петров",
                                null,
                                "Санкт-Петербург",
//                                new ArrayList<>(),
                                new Date(),
                                1L
//                                new ArrayList<>()
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
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("baduser@mail.com"));
    }
}