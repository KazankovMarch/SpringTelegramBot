package ru.adkazankov.spring_tbot.command.note;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.domain.Note;
import ru.adkazankov.spring_tbot.session.Session;
import ru.adkazankov.spring_tbot.session.Waiting;

@Component
public class NoteDeleteCommand extends NoteCrudCommand {
    @Override
    public String getName() {
        return "/delete";
    }

    @Override
    public SendMessage execute(Session session, String name) {
        if(name!=null){
            return confirmDeleteRequest(session,name);
        }
        else {
            return nameRequest(session);
        }
    }

    @Override
    public SendMessage takeContent(Session session, String content) {
        CrudState state = (CrudState) session.getState().getCommandState();
        if(state.getStep()==1){
            return confirmDeleteRequest(session, content);
        }
        else {
            return takeConfirm(session, content);
        }
    }

    private SendMessage confirmDeleteRequest(Session session, String name) {
        SendMessage sendMessage = new SendMessage();
        Note note = getNoteByNameAndChatId(session, name, sendMessage);
        if(note!=null){
            sendMessage.setText("Are you sure? type 'y' or '1'");
            session.getState().setWaiting(Waiting.Content);
            session.getState().setLastCommand(this);
            session.getState().setCommandState(new CrudState(2, name));
        }
        else {
            session.getState().setWaiting(Waiting.Command);
        }
        return sendMessage;
    }

    private SendMessage nameRequest(Session session) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please, specify name of the note you want to delete, or write "+CANCEL);
        session.getState().setWaiting(Waiting.Content);
        session.getState().setLastCommand(this);
        session.getState().setCommandState(new CrudState(1, null));
        return sendMessage;
    }

    private SendMessage takeConfirm(Session session, String content) {
        System.out.println("TAKING CONFIRM: "+content);
        session.getState().setWaiting(Waiting.Command);
        if(content.equals("y") || content.equals("1")) {
            SendMessage sendMessage = new SendMessage();
            String name = ((CrudState)session.getState().getCommandState()).name;
            Note note = getNoteByNameAndChatId(session, name, sendMessage);
            noteRepository.delete(note);
            sendMessage.setText("deleted.");
            return sendMessage;
        }
        else {
            return new SendMessage(session.getChatid(),"canceled.");
        }
    }

}
