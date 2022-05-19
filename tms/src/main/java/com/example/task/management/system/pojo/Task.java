package com.example.task.management.system.pojo;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

@Entity
@Table(name = "tm_task")
@Getter @Setter
public class Task extends BaseEntity implements Comparable<Task>, ITask {
    //private static long countOfTask = 1; for local db

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRIORITY", nullable = false)
    private Priority priority;

    @Column(name = "EXPECTED_END_DATE")
    private LocalDate expectedEndDate;

    @Column(name = "CURRENT_STATUS", nullable = false)
    private Status currentStatus;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<Note> notes = new LinkedList<>();

    @Builder
    public Task(String name,
                String description,
                Priority priority,
                LocalDate startDate,
                LocalDate expectedEndDate,
                Status currentStatus,
                Long userId) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.expectedEndDate = expectedEndDate;
        this.currentStatus = currentStatus;
        this.setStartDate(startDate);
        this.setUpdateDate(LocalDate.now());
        this.userId = userId;

//        this.idTask = countOfTask;
//        countOfTask++;
    }

    public Task() {

    }

    public Task updateTask(TaskDtoCreation detailsToUpdate) {
        setName(detailsToUpdate.getName());
        setDescription(detailsToUpdate.getDescription());
        setPriority(detailsToUpdate.getPriority());
        setStartDate(detailsToUpdate.getStartDate());
        setUpdateDate(LocalDate.now());
        setExpectedEndDate(detailsToUpdate.getExpectedEndDate());
        setCurrentStatus(detailsToUpdate.getCurrentStatus());
        setUserId(detailsToUpdate.getUserId());

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public int compareTo(Task b) {
        Task a = this;

        return new CompareToBuilder()
                .append(a.getPriority(), b.getPriority())
                .append(a.getExpectedEndDate(), b.getExpectedEndDate())
                .toComparison();
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", startDate=" + getStartDate() +
                ", updateDate=" + getUpdateDate() +
                ", expectedEndDate=" + expectedEndDate +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
