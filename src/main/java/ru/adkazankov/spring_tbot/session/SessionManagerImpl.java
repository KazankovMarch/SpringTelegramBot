package ru.adkazankov.spring_tbot.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionManagerImpl implements SessionManager, ActionListener {

    private Map<Long, SessionImpl> map;
    private long timeOut;
    private Timer timer;


    public SessionManagerImpl() {
        map = new HashMap<>();
        setTimeOut(1000 * 60);
    }

    @Override
    public SessionImpl getCurrentSession(Update update) {
        SessionImpl session = map.get(update.getMessage().getChatId());
        if(session==null ){
            session = new SessionImpl(update.getMessage().getChatId());
            map.put(session.getChatid(), session);
        }
        else {
            session.setIsNotnew();
        }
        session.setLastAccessTime(new Date().getTime());
        return session;
    }

    @Override
    public long getTimeOut() {
        return timeOut;
    }

    @Override
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        timer = new Timer((int) timeOut, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        long now = new Date().getTime();
        map.values().removeIf(session -> (session.getLastAccessTime() + timeOut < now));
    }
}
