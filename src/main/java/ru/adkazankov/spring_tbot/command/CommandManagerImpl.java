package ru.adkazankov.spring_tbot.command;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.adkazankov.spring_tbot.session.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.adkazankov.spring_tbot.log.MainLogger.log;

@Component
public class CommandManagerImpl implements CommandManager {

    private List<Command> commands = new ArrayList<>();

    @Override
    public SendMessage executeCommand(Session session, String msg) {
        Command command = findCommand(msg);
        if(command!=null) {
            int i = msg.indexOf(" ");
            String args = null;
            if (i > 0) {
                args = msg.substring(i+1);
            }

            log("Executing "+command.getName()+" withState "+session.getState().getCommandState()+" with args = "+args);

            SendMessage sendMessage = command.execute(session, args);
            sendMessage.setChatId(session.getChatid());
            return sendMessage;
        }
        else {
            return null;
        }
    }

    private Command findCommand(String msg) {
        for(Command command : commands){
            if(msg.equals(command.getName()) ||  msg.startsWith(command.getName()+" ")){
                return command;
            }
        }
        return null;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Autowired
    public void addAllCommands(Command... commands){
        this.commands.addAll(Arrays.asList(commands));
    }

}
