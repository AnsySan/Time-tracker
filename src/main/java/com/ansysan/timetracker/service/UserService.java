package com.ansysan.timetracker.service;

import com.ansysan.timetracker.dto.UserDto;
import com.ansysan.timetracker.entity.Role;
import com.ansysan.timetracker.entity.User;
import com.ansysan.timetracker.exception.DataValidationException;
import com.ansysan.timetracker.exception.EmailNotFoundException;
import com.ansysan.timetracker.exception.EntityNotFoundException;
import com.ansysan.timetracker.exception.UsernameAlreadyExistsException;
import com.ansysan.timetracker.mapper.UserMapper;
import com.ansysan.timetracker.repository.RoleRepository;
import com.ansysan.timetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    /**
     * Получает пользователя по его имени пользователя.
     *
     * @param username Имя пользователя для поиска.
     * @return Пользователь с указанным именем пользователя или {@code null}, если пользователь не найден.
     */
    public Optional<User> getUserByUsername(String username) {
        log.debug("getUserByUsername username: {}", username);
        return userRepository.findByUsername(username);}

    /**
     * Сохраняет данные о регистрации пользователя.
     *
     * @param userRegistrationDto Данные о регистрации пользователя для сохранения.
     */
    public User save(User userRegistrationDto) {
        log.debug("save user: {}", userRegistrationDto);
        User user = userMapper.toEntity(userRegistrationDto);
        Optional<Role> optionalRole = roleRepository.findByName("USER");
        if (optionalRole.isPresent()) {
            user.setRoles(optionalRole.get());
            userRepository.save(user);
        } else {
            throw new DataValidationException("Data not found");
        }
        return userRegistrationDto;
    }

    /**
     * Проверяет, существует ли пользователь с указанным именем пользователя.
     *
     * @param username Имя пользователя для проверки.
     * @return {@code true}, если пользователь существует, в противном случае {@code false}.
     */

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailNotFoundException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     */
    public void setRoleAdmin(Long id) {
        log.debug("setRoleAdmin role: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Optional<Role> optionalRole = roleRepository.findByName("ADMIN");
            if (optionalRole.isPresent()) {
                User user = userOptional.get();;
                Role role = optionalRole.get();
                user.setRoles(role);
            } else {
                throw new DataValidationException("Data not found");
            }
        } else {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    /**
     * Назначает роль пользователя пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль пользователя.
     */
    public void setRoleUser(Long id) {
        log.debug("setRoleUser role: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Optional<Role> optionalRole = roleRepository.findByName("USER");
            if (optionalRole.isPresent()) {
                User user = userOptional.get();
                Role role = optionalRole.get();
                user.setRoles(role);
                userRepository.save(user);
            } else {
                throw new DataValidationException("Data not found");
            }
        } else {
            throw new EntityNotFoundException("Entity not found");
        }
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список объектов {@link UserDto}, представляющих пользователей.
     */
    public List<UserDto> getAllUsers() {
        log.debug("getAllUsers");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }
}