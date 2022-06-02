package com.example.task.management.system.pojo;

import com.example.task.management.system.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class TaskFilter {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromExpectedEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toExpectedEndDate;

    @Column(name = "start_date")
    private LocalDate fromStartDate;
    @Column(name = "start_date")
    private LocalDate toStartDate;

    @Column(name = "update_date")
    private LocalDate fromUpdateDate;
    @Column(name = "update_date")
    private LocalDate toUpdateDate;

    private Status status;

    public TaskFilter(LocalDate fromExpectedEndDate,
                      LocalDate toExpectedEndDate,
                      LocalDate fromStartDate,
                      LocalDate toStartDate,
                      LocalDate fromUpdateDate,
                      LocalDate toUpdateDate,
                      Status status) {
        this.fromExpectedEndDate = fromExpectedEndDate;
        this.toExpectedEndDate = toExpectedEndDate;
        this.fromStartDate = fromStartDate;
        this.toStartDate = toStartDate;
        this.fromUpdateDate = fromUpdateDate;
        this.toUpdateDate = toUpdateDate;
        this.status = status;
    }
}
