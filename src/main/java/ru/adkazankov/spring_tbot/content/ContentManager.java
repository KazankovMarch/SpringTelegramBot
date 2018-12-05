package ru.adkazankov.spring_tbot.content;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;

public interface ContentManager {

    SendMessage sendContent(Session  session, String content);

}
