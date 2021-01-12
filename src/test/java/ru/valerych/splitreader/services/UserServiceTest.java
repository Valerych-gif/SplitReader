package ru.valerych.splitreader.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.valerych.splitreader.dto.UserDTO;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private final String
            ADMIN_USERNAME = "admin@mail.com",
            ADMIN_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            ADMIN_PASSWORD_DECODED = "1234",
            USER_USERNAME = "user@mail.com",
            USER_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            USER_PASSWORD_DECODED = "1234",
            BAD_USERNAME = "baduser@mail.com",
            BAD_PASSWORD = "12345";

    private final User user, admin;

    private final AuthenticationManager authManager = authentication -> {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if ((username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD_DECODED)) || (username.equals(USER_USERNAME) && password.equals(USER_PASSWORD_DECODED)))
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        return null;
    };

    public UserServiceTest() {
        user = new User(
                1L,
                USER_USERNAME,
                USER_PASSWORD_ENCODED,
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
        );

        admin = new User(
                2L,
                ADMIN_USERNAME,
                ADMIN_PASSWORD_ENCODED,
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
        );
        MockitoAnnotations.openMocks(this);
        when(userRepository.findUserByUsername(USER_USERNAME))
                .thenReturn(user);
        when(userRepository.findUserByUsername(ADMIN_USERNAME))
                .thenReturn(admin);

        when(roleService.getRoleByName("USER"))
                .thenReturn(new Role("USER"));

        when((passwordEncoder.encode(USER_PASSWORD_DECODED)))
                .thenReturn(USER_PASSWORD_ENCODED);
        when((passwordEncoder.encode(ADMIN_PASSWORD_DECODED)))
                .thenReturn(ADMIN_PASSWORD_ENCODED);
    }

    @Test
    @DisplayName("Loading user by username is success")
    void loadUserByUsernameSuccessTest() {
        UserDetails user = userService.loadUserByUsername(USER_USERNAME);
        Assertions.assertEquals(USER_PASSWORD_ENCODED, user.getPassword());
    }

    @Test
    @DisplayName("Loading user by username is failed")
    void loadUserByUsernameFailTest() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(BAD_USERNAME));
    }

    @Test
    @DisplayName("Getting user by username is success")
    void getUserByUsernameSuccessTest() {
        User user = userService.getUserByUsername(ADMIN_USERNAME);
        Assertions.assertEquals(ADMIN_PASSWORD_ENCODED, user.getPassword());
    }

    @Test
    @DisplayName("Getting user by username is fail")
    void getUserByUsernameFailTest() {
        User user = userService.getUserByUsername(BAD_USERNAME);
        Assertions.assertNull(user);
    }

    @Test
    @DisplayName("User creating test")
    void createUser() {

        User newUser = new User(
                null,
                USER_USERNAME,
                USER_PASSWORD_ENCODED,
                Collections.singletonList(new Role(null, "USER")),
                false,
                true,
                true,
                true,
                "",
                "",
                null,
                "",
//                                new ArrayList<Genre>(),
                null,
                null
//                                new ArrayList<Book>()
        );

        when(userRepository.save(newUser))
                .thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(USER_USERNAME);
        userDTO.setPassword(USER_PASSWORD_DECODED);
        userDTO.setConfirmPassword(USER_PASSWORD_DECODED);
        userDTO.setAcceptRules(true);
        User testUser = userService.createUser(userDTO);
        Assertions.assertEquals(USER_PASSWORD_ENCODED, testUser.getPassword());
    }

    @Test
    @DisplayName("Getting auth user is success")
    void getAuthUserSuccessTest() {
        authenticate(ADMIN_USERNAME, ADMIN_PASSWORD_DECODED);
        User testUser = userService.getAuthUser();

        Assertions.assertEquals(ADMIN_PASSWORD_ENCODED, testUser.getPassword());
    }

    @Test
    @DisplayName("Getting auth user is fail")
    void getAuthUserFailTest() {
        authenticate(ADMIN_USERNAME, BAD_PASSWORD);
        User testUser = userService.getAuthUser();

        Assertions.assertNull(testUser);
    }


    @Test
    @DisplayName("Getting auth userDTO is success")
    void getAuthUserDTOSuccessTest() {
        authenticate(USER_USERNAME, USER_PASSWORD_DECODED);
        UserDTO userDTO = userService.getAuthUserDTO();

        Assertions.assertEquals(USER_USERNAME, userDTO.getUsername());
        Assertions.assertEquals("******", userDTO.getPassword());
    }

    @Test
    @DisplayName("Getting auth userDTO is fail")
    void getAuthUserDTOFailTest() {
        authenticate(USER_USERNAME, BAD_PASSWORD);
        UserDTO userDTO = userService.getAuthUserDTO();

        Assertions.assertNull(userDTO);
    }

    @Test
    void updateAuthUserDTO() {
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }
}