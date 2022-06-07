package com.example.task.management.system.service;


import com.example.task.management.system.pojo.*;
import com.example.task.management.system.repo.NoteRepository;
import com.example.task.management.system.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DtoToEntityConverterImpl implements DtoToEntityConverter {

    @Value("${app.userUrlAllUsers}")
    private String userUrlAllUsers;

    @Autowired private NoteRepository noteRepository;

    @Autowired private RestTemplate restTemplate;
    //@Autowired private WebClient.Builder webClientBuilder;

    @Override
    public TaskDto toTaskDto(Task task) {
        Collection<Note> taskNotes = getTaskNotes(task);

        UserDto userDto = fetchUserDetails(task);

        TaskDto taskDto = TaskDto.builder()
                .idTask(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priority(task.getPriority())
                .startDate(task.getStartDate())
                .expectedEndDate(task.getExpectedEndDate())
                .currentStatus(task.getCurrentStatus())
                .notes(getCollectionNoteOfDto(taskNotes))
                .userId(task.getUserId())
                .userDto(userDto)
                .build();

        return taskDto;
    }

    @Override
    public Task getEntityTaskFromDto(TaskDto taskDto) {
        Task task = Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .startDate(taskDto.getStartDate())
                .expectedEndDate(taskDto.getExpectedEndDate())
                .currentStatus(taskDto.getCurrentStatus())
                .userId(taskDto.getUserId())
                .build();

        return task;
    }

    @Override
    public Task getEntityTaskFromUpdateDto(TaskDtoCreation taskDto) {
        Task task = Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .startDate(taskDto.getStartDate())
                .expectedEndDate(taskDto.getExpectedEndDate())
                .currentStatus(taskDto.getCurrentStatus())
                .userId(taskDto.getUserId())
                .build();

        return task;
    }

    @Override
    public Collection<TaskDto> getCollectionTaskOfDto(Collection<Task> tasks) {
        return tasks.stream().map(this::toTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public Note getEntityNoteFromDto(NoteDto noteDto, Task task) {
        return Note.builder()
                .task(task)
                .name(task.getName())
                .content(noteDto.getContent())
                .startDate(noteDto.getStartDate())
                .build();
    }

    @Override
    public NoteDto toNoteDto(Note note) {
        return NoteDto.builder()
                .content(note.getContent())
                .startDate(note.getStartDate())
                .build();
    }

    @Override
    public Collection<NoteDto> getCollectionNoteOfDto(Collection<Note> notes) {
        return notes.stream().map(this::toNoteDto)
                .collect(Collectors.toList());
    }

    private UserDto fetchUserDetails(Task task) {
        UserDto user = restTemplate.getForObject(userUrlAllUsers + task.getUserId(), UserDto.class);

        return user;
    }

    private Collection<Note> getTaskNotes(Task task) {
        Collection<Note> taskNotes = noteRepository.getAllNotes()
                .stream().filter(currentNote -> currentNote.getTask().getId() == task.getId())
                .collect(Collectors.toList());
        return taskNotes;
    }
}
