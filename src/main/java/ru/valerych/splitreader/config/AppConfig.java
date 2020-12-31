package ru.valerych.splitreader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:secret.properties")
public class AppConfig implements WebMvcConfigurer {

}
