package ru.valerych.splitreader.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class User{
    private String username;
    private String password;
    private List<Role> roles;

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public List<Role> getRoles(){
        return roles;
    }
}
