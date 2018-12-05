package ru.adkazankov.spring_tbot.command.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.command.Command;
import ru.adkazankov.spring_tbot.command.CommandState;
import ru.adkazankov.spring_tbot.domain.Note;
import ru.adkazankov.spring_tbot.repository.NoteRepository;
import ru.adkazankov.spring_tbot.session.Session;

import java.util.Optional;

@Component
public abstract class NoteCrudCommand implements Command {

    public static final String CANCEL = "/cancel";

    @Data
    protected static class CrudState extends CommandState {
        String name;

        public CrudState(int step, String name) {
            this.name = name;
            this.step = step;
        }

        @Override
        public String toString() {
            return "CrudState{" +
                    "name='" + name + '\'' +
                    ", step=" + step +
                    '}';
        }
    }

    protected NoteRepository noteRepository;

    Note getNoteByNameAndChatId(Session session, String name, SendMessage sendMessage){
        Optional<Note> optNote = noteRepository.findByChatIdAndName(session.getChatid(), name);
        if (optNote.isPresent()) {
            return optNote.get();
        } else {
            sendMessage.setText("Note not found by name = " + name);
            return null;
        }
    }

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

}
