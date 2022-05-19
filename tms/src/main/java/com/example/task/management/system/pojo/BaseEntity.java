package com.example.task.management.system.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Setter @Getter
public abstract class BaseEntity {

    @SequenceGenerator(name = "taskManagement", sequenceName = "taskManagement")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskManagement")
    @Column(name = "ID")
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "update_date")
    private LocalDate updateDate;
}
