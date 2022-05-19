package com.example.task.management.system.service;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.repo.NoteRepository;
import com.example.task.management.system.repo.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private DtoToEntityConverterImpl dtoToEntityConverter;

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {

        Task task1 = Task.builder()
                .name("Yarin")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.of(2022, 05, 17))
                .expectedEndDate(LocalDate.of(2022, 05, 22))
                .currentStatus(Status.OPEN)
                .build();

//        Mockito.when(taskRepository.findByName(task1.getName()))
//                .thenReturn(Optional.of(task1));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addNewTask() {
//        String name = "Yarin";
//        TaskDto found = taskService.getByName(name);
//
//        assertEquals(found.getName(), name);
    }

//    @Test
//    void updateTask() {
//    }
//
//    @Test
//    void deleteTask() {
//    }
//
//    @Test
//    void closeTask() {
//    }
//
//    @Test
//    void addNoteToTask() {
//    }
//
//    @Test
//    void getById() {
//    }
//
//    @Test
//    void getByName() {
//    }
//
//    @Test
//    void getByStatus() {
//    }
//
//    @Test
//    void getByDates() {
//    }
//
//    @Test
//    void getAllTask() {
//    }
}