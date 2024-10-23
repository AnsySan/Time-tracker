package com.ansysan.timetracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectDto {

    private long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
