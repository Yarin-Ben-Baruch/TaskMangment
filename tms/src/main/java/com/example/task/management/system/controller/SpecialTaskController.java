package com.example.task.management.system.controller;

import com.example.task.management.system.enums.Status;
import com.example.task.management.system.pojo.DelayTask;
import com.example.task.management.system.pojo.TaskDto;
import com.example.task.management.system.service.SpecialTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/specialTasks")
public class SpecialTaskController {
    private SpecialTaskService specialTaskService;

    @Autowired
    public void setSpecialTaskService(SpecialTaskService specialTaskService) {
        this.specialTaskService = specialTaskService;
    }

    @GetMapping("/mostCriticalTasks/{count}")
    public Collection<TaskDto> getMostCriticalTask(@PathVariable int count) {
        return specialTaskService.getTheMostCriticalTask(count);
    }

    @GetMapping("/delayTasks")
    public Collection<DelayTask> getDelayedTasks() {
        return specialTaskService.getDelayedTasks();
    }

    @GetMapping("/lastWeekTasks")
    public Map<Status, Collection<TaskDto>> getLastWeekTasks() {
        return specialTaskService.getLastWeekTasks();
    }

    @GetMapping("/openCriticalTask")
    public Collection<TaskDto> getOpenCriticalTask() {
        return specialTaskService.getOpenCriticalTask();
    }
}
