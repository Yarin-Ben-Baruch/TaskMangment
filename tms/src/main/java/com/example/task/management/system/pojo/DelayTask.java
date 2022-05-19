package com.example.task.management.system.pojo;

import lombok.Getter;

@Getter
public class DelayTask {
    private final TaskDto task;
    private final long numberOfDaysDelay;

    public DelayTask(TaskDto task, long numberOfDaysDelay) {
        this.task = task;
        this.numberOfDaysDelay = numberOfDaysDelay;
    }
}
