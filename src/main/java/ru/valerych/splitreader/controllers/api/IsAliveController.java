package ru.valerych.splitreader.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/isalive")
public class IsAliveController {

    @RequestMapping
    public String isAlive(){
        return "I am alive";
    }
}
