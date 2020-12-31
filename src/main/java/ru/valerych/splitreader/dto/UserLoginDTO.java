package ru.valerych.splitreader.dto;

public class UserLoginDTO {
    private String email;
    private String password;
    private String _csrf;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password, String csrf) {
        this.email = email;
        this.password = password;
        this._csrf = csrf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCsrf() {
        return _csrf;
    }

    public void setCsrf(String csrf) {
        this._csrf = csrf;
    }
}
