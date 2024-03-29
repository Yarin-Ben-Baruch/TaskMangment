package com.example.task.management.system.pojo;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;
import com.example.task.management.system.user.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter @Setter
public class TaskDto implements ITask {
    private Long idTask;

    private String name;

    private String description;

    private Priority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    //  private LocalDate updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedEndDate;

    private Status currentStatus;

    @JsonIgnore
    private Long userId;

    //Expands task class.
    private Collection<NoteDto> notes;

    private UserDto userDto;

    @Builder
    public TaskDto(Long idTask,
                   String name,
                   String description,
                   Priority priority,
                   LocalDate startDate,
                   LocalDate expectedEndDate,
                   Status currentStatus,
                   Collection<NoteDto> notes,
                   Long userId,
                   UserDto userDto) {
        this.idTask = idTask;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.currentStatus = currentStatus;
        this.notes = notes;
        this.userId = userId;
        this.userDto = userDto;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("name " + name)
                .append("description " + description)
                .append("priority " + priority)
                .append("startDate " + startDate)
                .append("expectedEndDate " + expectedEndDate)
                .append("currentStatus " + currentStatus)
                .toString();
    }
}
