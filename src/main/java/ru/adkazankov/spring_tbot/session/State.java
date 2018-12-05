package ru.adkazankov.spring_tbot.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.adkazankov.spring_tbot.command.Command;
import ru.adkazankov.spring_tbot.command.CommandState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class State {
    private Waiting waiting;
    private Command lastCommand;
    private CommandState commandState;
}
