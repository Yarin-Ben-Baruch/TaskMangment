package com.example.task.management.system.repo;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.Task;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


public interface TaskRepository {

    Collection<Task> getAllTask();

    Task addNewTask(Task newTask);

    Task updateTask(Task taskToUpdate);

    Optional<Task> findByTaskId(long id);

    void deleteTaskById(long id);

    Optional<Task> findTaskByName(String name);

    Collection<Task> findTaskByStatus(Status status);

    Collection<Task> findTaskByStartDates(LocalDate startDate, LocalDate endDate);

    Collection<Task> findTaskByExpectedDates(LocalDate startDate, LocalDate endDate);

    Collection<Task> findTaskByUpdateDates(LocalDate startDate, LocalDate endDate);

    Task saveTask(Task task);

    //special task
    Collection<Task> getTheMostCriticalTask(int count);

    Collection<Task> getTaskByStatusFilterByDays(Status statusToFilter, int days);

    Collection<Task> getDelayedTasks();

    Collection<Task> getOpenCriticalTask();
}
