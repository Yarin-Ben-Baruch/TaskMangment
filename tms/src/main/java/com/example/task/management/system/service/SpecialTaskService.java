package com.example.task.management.system.service;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.DelayTask;
import com.example.task.management.system.pojo.TaskDto;

import java.util.Collection;
import java.util.Map;

public interface SpecialTaskService {

    Collection<TaskDto> getTheMostCriticalTask(int count);

    Collection<DelayTask> getDelayedTasks();

    Map<Status, Collection<TaskDto>> getLastWeekTasks();

    Map<Status, Collection<TaskDto>> getTaskByStatus();

    Collection<TaskDto> getOpenCriticalTask();
}
