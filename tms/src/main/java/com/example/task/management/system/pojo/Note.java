package com.example.task.management.system.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TM_NOTE")
@Getter @Setter
public class Note extends BaseEntity {

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "NAME") private String name;

    @ManyToOne()
    @JoinColumn(name = "TASK_ID")
    private Task task;

    public Note() {

    }

    @Builder
    public Note(LocalDate startDate, String content, String name, Task task) {
        this.setStartDate(startDate);
        this.setUpdateDate(LocalDate.now());
        this.content = content;
        this.name = name;
        this.task = task;
    }
}
