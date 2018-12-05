package ru.adkazankov.spring_tbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;

public interface  Command {

    String getName();
    SendMessage execute(Session session, String args);
    SendMessage takeContent(Session session, String content);

}
