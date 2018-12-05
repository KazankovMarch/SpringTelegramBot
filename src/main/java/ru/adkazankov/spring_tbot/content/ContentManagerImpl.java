package ru.adkazankov.spring_tbot.content;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;
import ru.adkazankov.spring_tbot.session.Waiting;

import static ru.adkazankov.spring_tbot.log.MainLogger.log;

@Component
public class ContentManagerImpl implements ContentManager {

    public static final String CANCEL = "/cancel";

    @Override
    public SendMessage sendContent(Session session, String content) {
        SendMessage sendMessage = new SendMessage(session.getChatid(),"");
        if(content.equals(CANCEL)){
            sendMessage.setText("canceled.");
            session.getState().setWaiting(Waiting.Command);
        }
        else {
            log("TakingContent "+session.getState().getLastCommand().getName()+" withState "+session.getState().getCommandState()+" with args = "+content);
            sendMessage =  session.getState().getLastCommand().takeContent(session,content);
        }
        sendMessage.setChatId(session.getChatid());
        return sendMessage;
    }

}
