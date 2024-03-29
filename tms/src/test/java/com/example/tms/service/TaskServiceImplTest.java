package com.example.tms.service;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.pojo.TaskDto;
import com.example.task.management.system.pojo.TaskDtoCreation;
import com.example.task.management.system.repo.NoteRepository;
import com.example.task.management.system.repo.TaskRepository;
import com.example.task.management.system.service.DtoToEntityConverterImpl;
import com.example.task.management.system.service.TaskServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private DtoToEntityConverterImpl dtoToEntityConverter;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskDtoCreation taskDtoCreation;

    private Task task;

    TaskDto taskDto;

    @BeforeEach
    public void setup() {
        taskDtoCreation = TaskDtoCreation.builder()
                .name("Gal2")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.of(2022, 05, 9))
                .expectedEndDate(LocalDate.of(2022, 05, 25))
                .currentStatus(Status.OPEN)
                .userId(123l)
                .build();
        task = Task.builder()
                .name("Gal2")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.of(2022, 05, 9))
                .expectedEndDate(LocalDate.of(2022, 05, 19))
                .currentStatus(Status.OPEN)
                .userId(123l)
                .build();
        task.setId(1l);

        taskDto = TaskDto.builder()
                .name("Gal2")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.of(2022, 05, 9))
                .expectedEndDate(LocalDate.of(2022, 05, 19))
                .currentStatus(Status.OPEN)
                .userId(123l)
                .notes(new LinkedList<>())
                .build();
    }

    @Test
    @DisplayName("Should find Task by id")
    void getById() {

        Mockito.when(taskRepository.findByTaskId(123l)).thenReturn(Optional.of(task));
        Mockito.when(dtoToEntityConverter.toTaskDto(Mockito.any(Task.class))).thenReturn(taskDto);

        Assertions.assertThat(taskService.getById(123l).getName().equals(taskDto.getName()));
    }

    @Test
    void getByName() {
        Mockito.when(taskRepository.findTaskByName("Gal2")).thenReturn(Optional.of(task));
        Mockito.when(dtoToEntityConverter.toTaskDto(Mockito.any(Task.class))).thenReturn(taskDto);

        Assertions.assertThat(taskService.getByName("Gal2").getName().equals(taskDto.getName()));
    }

    @Test
    void getAllTask() {
        taskService.getAllTask();

        verify(taskRepository).getAllTask();
    }
}