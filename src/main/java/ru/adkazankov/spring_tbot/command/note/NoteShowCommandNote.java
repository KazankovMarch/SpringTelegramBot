package ru.adkazankov.spring_tbot.command.note;

import lombok.ToString;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.domain.Note;
import ru.adkazankov.spring_tbot.session.Session;

import java.util.Date;
import java.util.List;

@Component
@ToString
public class NoteShowCommandNote extends NoteCrudCommand {

    private String name = "/show";


    @Override
    public String getName() {
        return name;
    }

    @Override
    public SendMessage execute(Session session, String name) {
        SendMessage sendMessage = new SendMessage(session.getChatid(), "");
        if(name==null ||  name.equals("")){
            List<Note> notes = noteRepository.findAllByChatId(session.getChatid());
            notes.forEach(note -> append(sendMessage, note));
        }
        else {
            Note note = getNoteByNameAndChatId(session, name, sendMessage);
            if(note!=null){
                sendMessage.setText(note.toString());
            }
        }
        return sendMessage;
    }

    @Override
    public SendMessage takeContent(Session session, String content) {
        System.err.println(getClass().getName()+" "+(new Date().toString()));
        return new SendMessage(session.getChatid(), "ERROR, this command doesn't take content");
    }

    private void append(SendMessage sendMessage, Note note) {
        sendMessage.setText(sendMessage.getText() + note.toString() + "<------------->\n");
    }

}
