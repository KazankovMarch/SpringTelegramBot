package ru.adkazankov.spring_tbot.telegrambots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.adkazankov.spring_tbot.content.ContentManager;
import ru.adkazankov.spring_tbot.session.SessionImpl;
import ru.adkazankov.spring_tbot.session.SessionManager;
import ru.adkazankov.spring_tbot.command.CommandManager;

import static ru.adkazankov.spring_tbot.log.MainLogger.log;


@Component
@PropertySource(value = "classpath:bot.properties")
public class NotesBot extends TelegramLongPollingBot {

    private SessionManager sessionManager;

    private CommandManager commandManager;

    private ContentManager contentManager;

    @Value("${bot.token}")
    public String botToken;

    @Value("${bot.name}")
    private String botName;

    public void onUpdateReceived(Update update) {
        try {
            if(!update.hasMessage() || !update.getMessage().hasText()) return;

            log(update.getMessage().getText()+" <<___FROM "+update.getMessage().getFrom().toString());

            SessionImpl session = sessionManager.getCurrentSession(update);
            SendMessage message = null;
            String text = update.getMessage().getText();
            switch (session.getState().getWaiting()) {
                case Command: {
                    message = commandManager.executeCommand(session, text);
                    break;
                }
                case Content: {
                    message = contentManager.sendContent(session, text);
                    break;
                }
            }
            if(message!=null && message.getText()!="")
                execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }


    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Autowired
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Autowired
    public void setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
