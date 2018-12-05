package ru.adkazankov.spring_tbot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;

@Component
public class StartCommand implements Command{

    private HelpCommand helpCommand;

    @Override
    public String getName() {
        return "/start";
    }

    @Override
    public SendMessage execute(Session session, String args) {
        return helpCommand.execute(session, args);
    }

    @Override
    public SendMessage takeContent(Session session, String content) {
        return null;
    }

    @Autowired
    public void setHelpCommand(HelpCommand helpCommand) {
        this.helpCommand = helpCommand;
    }
}
