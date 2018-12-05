package ru.adkazankov.spring_tbot.session;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public interface SessionManager {

    SessionImpl getCurrentSession(Update update);
    long getTimeOut();
    void setTimeOut(long timeOut);


}
