package com.example.task.management.system.service;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.exceptions.TaskNotFoundException;
import com.example.task.management.system.pojo.DelayTask;
import com.example.task.management.system.pojo.Task;
import com.example.task.management.system.pojo.TaskDto;
import com.example.task.management.system.repo.NoteRepository;
import com.example.task.management.system.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class SpecialTaskServiceImpl implements SpecialTaskService {

    @Autowired private TaskRepository taskRepository;

    @Autowired private NoteRepository noteRepository;

    @Autowired private DtoToEntityConverter dtoToEntityConverter;

    @Override
    public Collection<TaskDto> getTheMostCriticalTask(int count) {
        if (count < 0) {
            throw new TaskNotFoundException("The number can not be negative");
        }

        return dtoToEntityConverter.getCollectionTaskOfDto(taskRepository.getTheMostCriticalTask(count));
    }

    @Override
    public Collection<DelayTask> getDelayedTasks() {
        return taskRepository.getDelayedTasks().stream()
                .map(currentTask -> new DelayTask(dtoToEntityConverter.toTaskDto(currentTask),
                        DAYS.between(LocalDate.now(), currentTask.getExpectedEndDate())))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Status, Collection<TaskDto>> getLastWeekTasks() {
        Map<Status, Collection<TaskDto>> allTaskOrderByStatus = new HashMap<>();
        int days = 7;
        Collection<Task> openStatus = taskRepository.getTaskByStatusFilterByDays(Status.OPEN, days);
        Collection<Task> inProcessStatus = taskRepository.getTaskByStatusFilterByDays(Status.IN_PROCESS, days);
        Collection<Task> closedStatus = taskRepository.getTaskByStatusFilterByDays(Status.CLOSED, days);

        allTaskOrderByStatus.put(Status.OPEN, dtoToEntityConverter.getCollectionTaskOfDto(openStatus));
        allTaskOrderByStatus.put(Status.IN_PROCESS, dtoToEntityConverter.getCollectionTaskOfDto(inProcessStatus));
        allTaskOrderByStatus.put(Status.CLOSED, dtoToEntityConverter.getCollectionTaskOfDto(closedStatus));

        return allTaskOrderByStatus;
    }


    @Override
    public Collection<TaskDto> getOpenCriticalTask() {
        return dtoToEntityConverter.getCollectionTaskOfDto(taskRepository.getOpenCriticalTask());
    }
}
