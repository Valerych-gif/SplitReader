package ru.valerych.splitreader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.valerych.splitreader.entities.Role;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{

    private String username;
    private String password;
    private List<Role> roles;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String city;
//    private List<Genre> preferredGenres;
    private Date lastVisit;
    private Long currentBookId;
//    private List<Book> books;



}
