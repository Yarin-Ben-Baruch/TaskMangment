package com.example.task.management.system.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @Builder
public class NoteDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private String content;
}
