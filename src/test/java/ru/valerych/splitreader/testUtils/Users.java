package ru.valerych.splitreader.testUtils;

import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;

import java.util.Collections;
import java.util.Date;

import static ru.valerych.splitreader.config.security.Roles.*;

public class Users {

    public static final String
            ADMIN_USERNAME = "admin@mail.com",
            ADMIN_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            ADMIN_PASSWORD_DECODED = "1234",
            USER_USERNAME = "user@mail.com",
            USER_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            USER_PASSWORD_DECODED = "1234",
            BAD_USERNAME = "baduser@mail.com",
            BAD_PASSWORD = "12345";

    public static User getAdmin(){
        return new User(
                2L,
                ADMIN_USERNAME,
                ADMIN_PASSWORD_ENCODED,
                Collections.singletonList(new Role(1L, ROLE_ADMIN.name())),
                true,
                true,
                true,
                true,
                "Петр",
                "Петров",
                null,
                "Санкт-Петербург",
                new Date(),
                1L
        );
    }

    public static User getUser(){
        return new User(
                1L,
                USER_USERNAME,
                USER_PASSWORD_ENCODED,
                Collections.singletonList(new Role(0L, ROLE_USER.name())),
                true,
                true,
                true,
                true,
                "Ivan",
                "Ivanov",
                null,
                "Moscow",
                new Date(),
                1L
        );
    }

    public static User getNewUser(){
        return new User(
                null,
                USER_USERNAME,
                USER_PASSWORD_ENCODED,
                Collections.singletonList(new Role(null, ROLE_USER.name())),
                false,
                true,
                true,
                true,
                "",
                "",
                null,
                "",
                null,
                null
        );
    }
}
