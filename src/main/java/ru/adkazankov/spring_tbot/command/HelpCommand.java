package ru.adkazankov.spring_tbot.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;

@Component
@PropertySource("bot.properties")
public class HelpCommand implements Command{

    @Value("${greeting}")
    private String greeting;

    @Override
    public String getName() {
        return "/help";
    }

    @Override
    public SendMessage execute(Session session, String args) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(greeting);
        return sendMessage;
    }

    @Override
    public SendMessage takeContent(Session session, String content) {
        return null;
    }
}
