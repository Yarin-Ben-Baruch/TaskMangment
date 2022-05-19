package com.example.task.management.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RangeDate {

    @JsonFormat(pattern = "yyyy-MM-dd") private final LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd") private final LocalDate endDate;
}
