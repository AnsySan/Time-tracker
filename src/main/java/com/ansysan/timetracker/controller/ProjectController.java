package com.ansysan.timetracker.controller;

import com.ansysan.timetracker.dto.ProjectDto;
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
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ProjectDto createProject(@RequestBody ProjectDto project) {
       return projectService.createProject(project);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_User')")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_User')")
    public ProjectDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public void updateProject(@PathVariable Long id, @RequestBody Project project) {
        projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @PostMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public void addUserToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.addUserToProject(userId, projectId);
    }

    @DeleteMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public void removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.deleteUserFromProject(userId, projectId);
    }
}