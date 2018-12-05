package ru.adkazankov.spring_tbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.adkazankov.spring_tbot.domain.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByChatIdAndName(Long chatId, String name);
    List<Note> findAllByChatId(Long chatId);
}
