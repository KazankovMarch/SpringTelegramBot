package ru.adkazankov.spring_tbot.command.note;

import lombok.ToString;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.domain.Note;
import ru.adkazankov.spring_tbot.session.Session;
import ru.adkazankov.spring_tbot.session.Waiting;

@Component
@ToString
public class NoteEditCommandNote extends NoteCrudCommand {

    @Override
    public String getName() {
        return "/edit";
    }

    @Override
    public SendMessage execute(Session session, String name) {
        if(name==null ||  name.equals("")){
            return nameRequest(session);
        }
        else {
            return editRequest(session,name);
        }
    }

    @Override
    public SendMessage takeContent(Session session, String content) {
        System.out.println("TAKEN CONTENT (EDIT): "+content);
        CrudState state = (CrudState) session.getState().getCommandState();
        if(state.getStep()==1){
            return editRequest(session, content);
        }
        else {
            return takeEdit(session, content);
        }

    }

    private SendMessage nameRequest(Session session) {
        session.getState().setCommandState(new CrudState(1, null));
        session.getState().setWaiting(Waiting.Content);
        session.getState().setLastCommand(this);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please, specify name of the note you want to edit, or write "+CANCEL);
        return sendMessage;
    }

    private SendMessage editRequest(Session session, String name) {
        SendMessage sendMessage = new SendMessage();
        Note note = getNoteByNameAndChatId(session, name, sendMessage);
        if (note != null) {
            sendMessage.setText("Now you can write/paste note or "+CANCEL);
            session.getState().setWaiting(Waiting.Content);
            session.getState().setLastCommand(this);
            session.getState().setCommandState(new CrudState(2,name));
        }
        else {
            session.getState().setWaiting(Waiting.Command);
        }
        return sendMessage;
    }



    private SendMessage takeEdit(Session session, String content) {
        session.getState().setWaiting(Waiting.Command);
        if(content.equals(CANCEL)){
            return new SendMessage(session.getChatid(), "canceled.");
        }
        else {
            SendMessage message = new SendMessage();
            String name = ((CrudState)session.getState().getCommandState()).name;
            Note note = getNoteByNameAndChatId(session,name,message);
            if(note!=null){
                note.setBody(content);
                noteRepository.save(note);
                message.setText("Saved.");
            }
            return message;
        }
    }


}
