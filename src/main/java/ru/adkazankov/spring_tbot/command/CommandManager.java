package ru.adkazankov.spring_tbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;

public interface CommandManager {

    SendMessage executeCommand(Session session, String msg);

    void addCommand(Command command);

}
