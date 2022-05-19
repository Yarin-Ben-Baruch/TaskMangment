package com.example.task.management.system.repo;

import com.example.task.management.system.pojo.Note;

import java.util.Collection;

public interface NoteRepository {

    Note saveNote(Note note);

    Collection<Note> findAllNotesByName(String taskName);

    Collection<Note> getAllNotes();
}
