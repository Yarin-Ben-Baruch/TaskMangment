package com.example.task.management.system.repo.local;

import com.example.task.management.system.pojo.Note;
import com.example.task.management.system.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@Profile("Local")
public class NoteRepositoryLocalImpl implements NoteRepository {

    @Autowired private Db db;

    @Override
    public Collection<Note> getAllNotes() {
        return db.getNotes();
    }

    @Override
    public Note saveNote(Note note) {
        db.getNotes().add(note);

        return note;
    }

    @Override
    public Collection<Note> findAllNotesByName(String taskName) {
        return db.getNotes().stream()
                .filter(currentNote -> currentNote.getName().equals(taskName))
                .collect(Collectors.toList());
    }
}
