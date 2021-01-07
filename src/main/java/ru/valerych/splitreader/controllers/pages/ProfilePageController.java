package ru.valerych.splitreader.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilePageController {

    @GetMapping("/profile")
    public String getProfilePage(){
        return "profile.html";
    }
}
