package com.example.task.management.system.service;

import com.example.task.management.system.pojo.*;

import java.util.Collection;

public interface DtoToEntityConverter {

    TaskDto toTaskDto(Task task);

    Task getEntityTaskFromDto(TaskDto taskDto);

    Task getEntityTaskFromUpdateDto(TaskDtoCreation taskDto);

    Collection<TaskDto> getCollectionTaskOfDto(Collection<Task> tasks);

    Note getEntityNoteFromDto(NoteDto noteDto, Task task);

    NoteDto toNoteDto(Note note);

    Collection<NoteDto> getCollectionNoteOfDto(Collection<Note> notes);
}
