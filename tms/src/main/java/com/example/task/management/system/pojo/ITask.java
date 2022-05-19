package com.example.task.management.system.pojo;

import com.example.task.management.system.enums.Priority;
import com.example.task.management.system.enums.Status;

import java.time.LocalDate;

/**
 * User: yarin_b
 * Date: 15-מאי-2022
 */
public interface ITask {

    String getName();

    Priority getPriority();

    LocalDate getExpectedEndDate();

    Status getCurrentStatus();

    Long getUserId();

    void setCurrentStatus(Status status);
}
