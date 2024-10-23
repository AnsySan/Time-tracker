package com.ansysan.timetracker.controller;

import com.ansysan.timetracker.dto.UserDto;
import com.ansysan.timetracker.entity.MessageResponse;
import com.ansysan.timetracker.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     * @return Ответ с сообщением об успешном изменении роли пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/admin/{id}")
    @Operation(
            summary = "Set the administrator role for the user",
            description = "Update the user role by specifying its id. The response is a message about the successful changed a role",
            tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public ResponseEntity<?> setAdmin(@PathVariable("id") @Min(1) Long id) {
        log.info("Set administrator role for the user");
        adminService.setAdmin(id);
        return ResponseEntity.ok(new MessageResponse("User role Admin"));
    }

    /**
     * Назначает роль пользователь пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль журналиста.
     * @return Ответ с сообщением об успешном изменении роли пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/user/{id}")
    @Operation(
            summary = "Set the user role for the user",
            description = "Update the user role by specifying its id. The response is a message about the successful changed a role",
            tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public ResponseEntity<?> setUser(@PathVariable("id") @Min(1) Long id) {
        log.info("Set user role for the user");
        adminService.setUser(id);
        return ResponseEntity.ok(new MessageResponse("User role Admin"));
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Ответ со списком объектов {@link UserDto}, представляющих пользователей.
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Retrieve all users",
            description = "Collect all users. The answer is an array of users with identifier, username and role for each of the array element",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public List<UserDto> getAllUsers() {
        log.info("Retrieve all users");
        return adminService.getAllUsers();
    }
}
