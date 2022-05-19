package com.example.task.management.system.service;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.enums.TimeOptions;
import com.example.task.management.system.pojo.NoteDto;
import com.example.task.management.system.pojo.TaskDto;
import com.example.task.management.system.pojo.TaskDtoCreation;

import java.time.LocalDate;
import java.util.Collection;

public interface TaskService {

    TaskDto addNewTask(TaskDtoCreation newTask);

    TaskDto updateTask(TaskDtoCreation detailsToUpdate, long id);

    void deleteTask(long id);

    TaskDto closeTask(long id);

    NoteDto addNoteToTask(NoteDto noteToAdd, long id);

    TaskDto getById(long id);

    TaskDto getByName(String name);

    Collection<TaskDto> getByStatus(Status status);

    Collection<TaskDto> getByDates(LocalDate startDate, LocalDate endDate, TimeOptions timeOptions);

    Collection<TaskDto> getAllTask();
}
