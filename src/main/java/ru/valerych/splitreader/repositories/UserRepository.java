package ru.valerych.splitreader.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.valerych.splitreader.entities.Role;
import ru.valerych.splitreader.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User(
                "user@mail.com",
                "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
                Arrays.asList(new Role("USER"))
                ));

        users.add(new User(
                "admin@mail.com",
                "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
                Arrays.asList(new Role("ADMIN"))
                ));
    }

    public User findUserByUserName(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public User findUserByUserNameAndPassword(String email, String password) {
        User user = findUserByUserName(email);
        if (user!=null&&user.getPassword().equals(password)){
            return user;
        }
        throw new RuntimeException("User " + email + " not found");
    }
}
