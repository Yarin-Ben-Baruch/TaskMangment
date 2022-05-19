package com.example.task.management.system.pojo;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TaskDtoCreation implements ITask {

    @Setter(AccessLevel.PRIVATE)
    private String name;

    private String description;

    private Priority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedEndDate;

    private Status currentStatus;

    private Long userId;

    @Builder
    public TaskDtoCreation(String name,
                           String description,
                           Priority priority,
                           LocalDate startDate,
                           LocalDate expectedEndDate,
                           Status currentStatus,
                           Long userId) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.currentStatus = currentStatus;
        this.userId = userId;
    }

    public Task updateTask(TaskDtoCreation detailsToUpdate, Task taskToUpdate) {
        taskToUpdate.setName(detailsToUpdate.getName());
        taskToUpdate.setDescription(detailsToUpdate.getDescription());
        taskToUpdate.setPriority(detailsToUpdate.getPriority());
        taskToUpdate.setStartDate(detailsToUpdate.getStartDate());
        taskToUpdate.setExpectedEndDate(detailsToUpdate.getExpectedEndDate());
        taskToUpdate.setCurrentStatus(detailsToUpdate.getCurrentStatus());
        taskToUpdate.setUserId(taskToUpdate.getUserId());

        return taskToUpdate;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nname " + name)
                .append("\ndescription " + description)
                .append("\npriority " + priority)
                .append("\nstartDate " + startDate)
                .append("\nexpectedEndDate " + expectedEndDate)
                .append("\ncurrentStatus " + currentStatus)
                .append("\nuserId " + userId)
                .toString();
    }
}
