package ru.valerych.splitreader.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.repositories.UserRepository;
import ru.valerych.splitreader.services.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllersTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpServletRequest request;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

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

    public ControllersTest() {
        MockitoAnnotations.openMocks(this);

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

    private String getCsrf() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/v1/csrf-token"))
                .andReturn();
        String csrfString = mvcResult.getResponse().getContentAsString();
        JsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> csrfMap = jsonParser.parseMap(csrfString);
        return (String) csrfMap.get("token");
    }

    @Test
    @DisplayName("Is alive controller check")
    public void isAliveControllerTest() throws Exception {
        mockMvc
                .perform(get("/api/v1/isalive"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Csrf-token controller check")
    public void getCsrfControllerTest() throws Exception {
        mockMvc
                .perform(get("/api/v1/csrf-token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andExpect(content().string(containsString("parameterName")))
                .andExpect(content().string(containsString("_csrf")))
                .andExpect(content().string(containsString("headerName")))
                .andExpect(content().string(containsString("X-CSRF-TOKEN")));
    }

    @Test
    @DisplayName("User authorization test")
    @Disabled
    public void userAuthorizationTest() throws Exception {
        String csrfToken = getCsrf();
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("username", "user@mail.com");
        requestParams.add("password", "1234");
        requestParams.add("_csrf", csrfToken);

        mockMvc
                .perform(post("/authuser")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(requestParams)
                )
                .andDo(print());
    }


    @Test
    @Disabled
    void getUserDetails() throws Exception {
        login(USER_USERNAME, USER_PASSWORD_DECODED);

        mockMvc
                .perform(get("/api/v1/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString(USER_USERNAME)));
    }

    public void login(String user, String pass) {
        UsernamePasswordAuthenticationToken authRequest
                = new UsernamePasswordAuthenticationToken(user, pass);
        Authentication auth = authManager.authenticate(authRequest);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }
}