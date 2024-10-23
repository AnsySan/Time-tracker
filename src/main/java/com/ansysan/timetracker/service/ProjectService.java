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

    /**
     *Метод для проверки существования проекта по его ID. Если проект не найден, выбрасывается исключение DataValidationException с сообщением об ошибке.
     */
    private Project existsProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new DataValidationException("Project with ID " + projectId + " not found"));
    }

    /**
     * Метод для проверки существования пользователя по его ID.Если пользователь не найден, выбрасывается исключение DataValidationException с сообщением об ошибке.
      */
    private User existsUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataValidationException("User with ID " + userId + " not found"));
    }

    /**
     *  Метод для создания нового проекта.
    */
    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = mapper.toEntity(projectDto);
        projectRepository.save(project);

        return mapper.toDto(project);
    }

    /**
     *Метод для получения списка всех проектов.
     */
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     *Метод для получения проекта по его ID.
     */
    public ProjectDto getProjectById(Long id) {
        Project projectEntity = existsProject(id);
        return mapper.toDto(projectEntity);
    }

    /**
     *  Метод для обновления информации о проекте по его ID.
    */
    @Transactional
    public ProjectDto updateProject(Long id, Project project) {
        Project existProject = existsProject(id);

        existProject.setName(project.getName());
        projectRepository.save(existProject);

        return mapper.toDto(existProject);
    }

    /**
     *  Метод для удаления проекта по его ID.
    */
    @Transactional
    public ProjectDto deleteProject(Long id) {
        Project project = existsProject(id);

        return mapper.toDto(project);
    }

    /**
     *  Метод для добавления пользователя в проект.
    */
    @Transactional
    public ProjectDto addUserToProject(Long userId, Long projectId) {
        Project project = existsProject(projectId);
        User user = existsUser(userId);
        project.getUsers().add(user);

        projectRepository.save(project);

        return mapper.toDto(project);
    }

    /**
     *  Метод для удаления пользователя из проекта.
    */
    @Transactional
    public ProjectDto deleteUserFromProject(Long userId, Long projectId) {
        Project projectEntity = existsProject(projectId);
        User userEntity = existsUser(userId);
        projectEntity.getUsers().remove(userEntity);
        projectRepository.save(projectEntity);

        return mapper.toDto(projectEntity);
    }

}