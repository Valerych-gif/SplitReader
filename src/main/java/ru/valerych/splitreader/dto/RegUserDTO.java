package ru.valerych.splitreader.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegUserDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private boolean acceptRules;
}
