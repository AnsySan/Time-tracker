package com.ansysan.timetracker.controller;

import com.ansysan.timetracker.dto.ProjectDto;
import com.ansysan.timetracker.dto.UserDto;
import com.ansysan.timetracker.entity.Project;
import com.ansysan.timetracker.entity.User;
import com.ansysan.timetracker.service.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Создаёт проект.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto createProject(@RequestBody ProjectDto project) {
       return projectService.createProject(project);
    }

    /**
     *Возвращает все проекты.
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * Возвращает проект по индентификатору.
     * @param id индентификатор по которому возвращентся проект.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ProjectDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    /**
     * Обновляет Проект.
     * @param id индентификатор проекта, что обновляется.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateProject(@PathVariable Long id, @RequestBody Project project) {
        projectService.updateProject(id, project);
    }

    /**
     * Удаляет проект.
     * @param id индентификатор проекта, что удаляется.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    /**
     * Добавляет Пользователя в проект.
     * @param projectId индентификатор проекта.
     * @param userId индентификатор пользователя.
     */
    @PostMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void addUserToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.addUserToProject(userId, projectId);
    }

    /**
     * Удаляеи Пользователя с проекта.
     * @param projectId индентификатор проекта.
     * @param userId индентификатор пользователя.
     */
    @DeleteMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.deleteUserFromProject(userId, projectId);
    }
}