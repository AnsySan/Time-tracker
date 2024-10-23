package com.ansysan.timetracker.service;

import com.ansysan.timetracker.dto.ProjectDto;
import com.ansysan.timetracker.entity.Project;
import com.ansysan.timetracker.entity.User;
import com.ansysan.timetracker.exception.DataValidationException;
import com.ansysan.timetracker.mapper.ProjectMapper;
import com.ansysan.timetracker.repository.ProjectRepository;
import com.ansysan.timetracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper mapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    private Project existsProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new DataValidationException("Project with ID " + projectId + " not found"));
    }

    private User existsUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataValidationException("User with ID " + userId + " not found"));
    }

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = mapper.toEntity(projectDto);
        projectRepository.save(project);

        return mapper.toDto(project);
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ProjectDto getProjectById(Long id) {
        Project projectEntity = existsProject(id);
        return mapper.toDto(projectEntity);
    }

    @Transactional
    public ProjectDto updateProject(Long id, Project project) {
        Project existProject = existsProject(id);

        existProject.setName(project.getName());
        projectRepository.save(existProject);

        return mapper.toDto(existProject);
    }

    public ProjectDto deleteProject(Long id) {
        Project project = existsProject(id);

        return mapper.toDto(project);
    }

    @Transactional
    public ProjectDto addUserToProject(Long userId, Long projectId) {
        Project project = existsProject(projectId);
        User user = existsUser(userId);
        project.getUsers().add(user);

        projectRepository.save(project);

        return mapper.toDto(project);


    }

    @Transactional
    public ProjectDto deleteUserFromProject(Long userId, Long projectId) {
        Project projectEntity = existsProject(projectId);
        User userEntity = existsUser(userId);
        projectEntity.getUsers().remove(userEntity);
        projectRepository.save(projectEntity);

        return mapper.toDto(projectEntity);

    }
}