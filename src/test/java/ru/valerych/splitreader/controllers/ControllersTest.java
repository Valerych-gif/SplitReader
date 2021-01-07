package ru.valerych.splitreader.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import ru.valerych.splitreader.entities.Book;
import ru.valerych.splitreader.entities.Genre;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;
import ru.valerych.splitreader.repositories.UserRepository;
import ru.valerych.splitreader.services.UserService;

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
    HttpSession session;

    @Mock
    private UserRepository userRepository;

    public ControllersTest() {
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
//                                new ArrayList<>(),
                                new Date(),
                                1L
//                                new ArrayList<>()
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
}