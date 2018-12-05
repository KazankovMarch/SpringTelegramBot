package ru.adkazankov.spring_tbot.session;

import lombok.ToString;

import java.util.Date;

import static ru.adkazankov.spring_tbot.session.Waiting.Command;

@ToString
public class SessionImpl implements Session{

    private State state;
    private Long chatId;
    private long lastAccessTime;
    private boolean isNew;

    public SessionImpl(Long chatId) {
        this.state = new State(Command, null,null);
        this.chatId = chatId;
        this.lastAccessTime = new Date().getTime();
        isNew = true;
    }

    @Override
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public Long getChatid() {
        return chatId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public void setIsNotnew() {
        isNew = false;
    }

    @Override
    public State getState() {
        return state;
    }

}
