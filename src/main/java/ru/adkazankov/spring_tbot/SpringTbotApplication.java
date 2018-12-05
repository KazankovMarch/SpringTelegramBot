package ru.adkazankov.spring_tbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.logging.Logger;


@SpringBootApplication
public class SpringTbotApplication {


    public static void main(String[] args) {

        ApiContextInitializer.init();

        SpringApplication.run(SpringTbotApplication.class, args);
    }

}
