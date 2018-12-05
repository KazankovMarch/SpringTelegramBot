package ru.adkazankov.spring_tbot.log;

import ru.adkazankov.spring_tbot.SpringTbotApplication;

import java.util.logging.Logger;

public class MainLogger {

    private static final Logger LOGGER = Logger.getLogger(SpringTbotApplication.class.toString());

    public static void log(String s){
        LOGGER.info(s);
    }

    public static void err(String s){
        LOGGER.severe(s);
    }

}
