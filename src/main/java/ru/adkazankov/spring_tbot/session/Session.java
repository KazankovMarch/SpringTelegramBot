package ru.adkazankov.spring_tbot.session;

import javafx.util.Pair;
import ru.adkazankov.spring_tbot.command.Command;

import java.util.List;

public interface Session {

    State getState();
    long getLastAccessTime();
    Long getChatid();
    boolean isNew();
    void setIsNotnew();

}
