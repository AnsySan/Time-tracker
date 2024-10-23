package com.ansysan.timetracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordDto {

    private long id;

    private String title;

    private LocalDateTime createdAt;

}
