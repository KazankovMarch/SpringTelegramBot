package ru.adkazankov.spring_tbot.command.note;

import lombok.ToString;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.domain.Note;
import ru.adkazankov.spring_tbot.session.Session;
import ru.adkazankov.spring_tbot.session.Waiting;

@Component
@ToString
public class NoteCreateCommand extends NoteCrudCommand {


    public static final String CANCEL = "/cancel";

    @Override
    public String getName() {
        return "/create";
    }

    @Override
    public SendMessage execute(Session session, String name) {
        SendMessage sendMessage = new SendMessage();
        if(name==null ||  name.equals("")){
            sendMessage.setText("Please, specify note name, e.g. 'note about Ann', or write '/cancel'");
            session.getState().setWaiting(Waiting.Content);
            session.getState().setLastCommand(this);
            session.getState().setCommandState(new CrudState(1,null));
        }
        else {
            setName(session,name,sendMessage);
        }
        return sendMessage;
    }


    @Override
    public SendMessage takeContent(Session session, String content) {
        SendMessage sendMessage = new SendMessage();
        CrudState state = ((CrudState)session.getState().getCommandState());
        if(state.getStep() == 1){
            setName(session,content,sendMessage);
        }
        else {
            setBodyAndSave(session,content,sendMessage);
        }
        return sendMessage;
    }

    private void setName(Session session, String name, SendMessage sendMessage) {
        Note note = getNoteByNameAndChatId(session, name, sendMessage);
        if (note != null) {
            sendMessage.setText("Sorry, there is note with the same name already.");
            session.getState().setWaiting(Waiting.Command);
        } else {
            sendMessage.setText("Now you can write/paste note or "+CANCEL);
            session.getState().setWaiting(Waiting.Content);
            session.getState().setLastCommand(this);
            session.getState().setCommandState(new CrudState(2,name));
        }
    }

    private void setBodyAndSave(Session session, String content, SendMessage sendMessage) {
        System.out.println("SAVING CONTENT  "+content);
        session.getState().setWaiting(Waiting.Command);
        if(content.equals(CANCEL)){
            sendMessage.setText("canceled.");
        }
        else {
            String name = ((CrudState) session.getState().getCommandState()).name;
            Note note = new Note();
            note.setChatId(session.getChatid());
            note.setName(name);
            note.setBody(content);
            note.setId(null);
            noteRepository.save(note);
            sendMessage.setText("created.");
        }
    }
}
