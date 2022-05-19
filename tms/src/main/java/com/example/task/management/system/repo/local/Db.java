package com.example.task.management.system.repo.local;

import com.example.task.management.system.pojo.Note;
import com.example.task.management.system.pojo.Task;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;

@Getter
@Component
public class Db {

    private final Collection<Task> tasks;
    private final Collection<Note> notes;

    public Db() {
        this.tasks = new LinkedList<>();
        this.notes = new LinkedList<>();
    }

    public void clearTasksDb() {
        tasks.clear();
    }

    public void clearNoteDb() {
        tasks.clear();
    }
}
