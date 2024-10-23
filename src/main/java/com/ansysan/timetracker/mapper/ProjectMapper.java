package com.ansysan.timetracker.mapper;

import com.ansysan.timetracker.dto.ProjectDto;
import com.ansysan.timetracker.entity.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toEntity(ProjectDto projectDto);

}
