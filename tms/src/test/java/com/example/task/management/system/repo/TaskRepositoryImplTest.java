package com.example.task.management.system.repo;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.pojo.TaskDtoCreation;
import com.example.task.management.system.repo.local.Db;
import com.example.task.management.system.repo.local.TaskRepositoryLocalImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryImplTest {

    @Mock
    private Db db;

    @InjectMocks
    private TaskRepositoryLocalImpl underTest;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void beforeMethod() {
        task1 = Task.builder()
                .name("Yarin")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.of(2022, 05, 17))
                .expectedEndDate(LocalDate.of(2022, 05, 22))
                .currentStatus(Status.OPEN)
                .build();

        task2 = Task.builder()
                .name("Lior")
                .description("no description")
                .priority(Priority.LOW)
                .startDate(LocalDate.of(2022, 05, 16))
                .expectedEndDate(LocalDate.of(2022, 05, 20))
                .currentStatus(Status.IN_PROCESS)
                .build();

        when(db.getTasks()).thenReturn(new ArrayList<>(Arrays.asList(task1, task2)));
    }

    @Test
    void getAllTask() {
        when(db.getTasks()).thenReturn(new ArrayList<>(Arrays.asList(task1, task2)));

        assertEquals(2, underTest.getAllTask().size());
    }

    @Test
    void addNewTask() {
        Task task1 = underTest.addNewTask(Task.builder()
                .name("NewTask")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.now())
                .expectedEndDate(LocalDate.of(2022, 05, 22))
                .currentStatus(Status.OPEN)
                .build());

        assertEquals(3, underTest.getAllTask().size());
    }

    @Test
    void updateTask() {
        TaskDtoCreation taskDtoUpdate = TaskDtoCreation.builder().name("No yarin").build();
        Task taskUnderTest = underTest.findTaskByName("Yarin").get();
        taskUnderTest.updateTask(taskDtoUpdate);

        assertEquals(taskUnderTest.getName(), "No yarin");
        assertNotEquals(taskUnderTest.getName(), "Yarin");
    }

//      generte value in db
//    @Test
//    void findById() {
//        Task taskUnderTest = underTest.findTaskById(2).get();
//
//        assertEquals(taskUnderTest, task2);
//    }
//
//    @Test
//    void deleteById() {
//        underTest.deleteById(4);
//
//        assertEquals(4, underTest.getAllTask().size());
//    }

    @Test
    void findByName() {
        assertEquals(underTest.findTaskByName("Yarin").get(), task1);
    }

    @Test
    void findByStatus() {
        assertEquals(underTest.findTaskByStatus(Status.OPEN).size(), 1);
        assertEquals(underTest.findTaskByStatus(Status.IN_PROCESS).size(), 1);
        assertEquals(underTest.findTaskByStatus(Status.CLOSED).size(), 0);
    }

    @Test
    void findByStartDates() {
        Collection<Task> result = underTest.findTaskByStartDates(LocalDate.of(2022, 05, 14),
                LocalDate.of(2022, 05, 17));

        assertEquals(result.size(), 1);
    }

    @Test
    void findByExecutedDates() {
        Collection<Task> result = underTest.findTaskByExpectedDates(LocalDate.of(2022, 05, 14),
                LocalDate.of(2022, 05, 17));

        assertEquals(result.size(), 0);
    }

    @Test
    void findByUpdateDates() {
        Collection<Task> result = underTest.findTaskByUpdateDates(LocalDate.of(2022, 05, 14),
                LocalDate.of(2022, 05, 18));

        assertEquals(result.size(), 0);
    }

    @Test
    void save() {
        Task task3 = underTest.saveTask(Task.builder()
                .name("NewTask")
                .description("no description")
                .priority(Priority.MEDIUM)
                .startDate(LocalDate.now())
                .expectedEndDate(LocalDate.of(2022, 05, 22))
                .currentStatus(Status.OPEN)
                .build());

        Task taskUnderTest = underTest.findTaskByName("NewTask").get();

        assertEquals(taskUnderTest, task3);
    }
}