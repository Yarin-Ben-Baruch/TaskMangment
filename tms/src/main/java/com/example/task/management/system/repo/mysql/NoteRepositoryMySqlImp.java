package com.example.task.management.system.repo.mysql;

import com.example.task.management.system.pojo.Note;
import com.example.task.management.system.repo.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Profile("Db")
public class NoteRepositoryMySqlImp implements NoteRepository {

    @Autowired NoteRepositoryMySql noteRepositoryMySql;

    @Override
    public Collection<Note> getAllNotes() {
        return noteRepositoryMySql.findAll();
    }

    @Override
    public Collection<Note> findAllNotesByName(String taskName) {
        return noteRepositoryMySql.findByName(taskName);
    }

    @Override
    public Note saveNote(Note note) {
        return noteRepositoryMySql.save(note);
    }
}
