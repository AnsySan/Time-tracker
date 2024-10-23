package com.ansysan.timetracker.service;

import com.ansysan.timetracker.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     */
    public void setAdmin(Long id) {
        log.debug("Setting admin to " + id);
        userService.setRoleAdmin(id);
    }

    /**
     * Назначает роль пользователь пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль пользователь.
     */
    public void setUser(Long id) {
        log.debug("Setting journalist to " + id);
        userService.setRoleUser(id);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список объектов {@link UserDto}, представляющих пользователей.
     */
    public List<UserDto> getAllUsers() {
        log.debug("Getting all users");
        return userService.getAllUsers();
    }
}