package com.ansysan.timetracker.service;

import com.ansysan.timetracker.dto.RecordDto;
import com.ansysan.timetracker.entity.Project;
import com.ansysan.timetracker.entity.User;
import com.ansysan.timetracker.entity.Record;
import com.ansysan.timetracker.exception.NotFoundException;
import com.ansysan.timetracker.mapper.RecordMapper;
import com.ansysan.timetracker.repository.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordMapper mapper;
    private final RecordRepository recordRepository;

    /**
     *Метод для получения записи по её ID.Если запись не найдена, выбрасывается исключение NotFoundException с сообщением об ошибке.
     */
    public Record getRecord(Long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("Record not found"));
    }

    /**
     *  Метод для получения проекта по его ID.Если проект не найден, выбрасывается исключение NotFoundException с сообщением об ошибке.
    */
    public Project getProject(Long projectId) {
        return recordRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found")).getProject();
    }

    /**
     *  Метод для получения пользователя по его ID.Если запись не найдена, выбрасывается исключение NotFoundException с сообщением об ошибке.
    */
    public User getUser(Long userId) {
        return recordRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Record not found")).getUser();
    }

    /** Метод для создания новой записи.
     *
     */
    public void createRecord(RecordDto recordRequest, Long userId, Long projectId) {
        User userEntity = getUser(userId);
        Project projectEntity = getProject(projectId);

        // Проверка, что пользователь принадлежит проекту.
        if (!projectEntity.getUsers().contains(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }

        // Преобразование DTO в сущность и связывание с пользователем и проектом.
        Record recordEntity = mapper.toEntity(recordRequest);
        recordEntity.setUser(userEntity);
        recordEntity.setProject(projectEntity);
        recordRepository.save(recordEntity);
    }

    /**
     * Метод для получения списка всех записей.
     */
    public List<RecordDto> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     *Метод для получения записи по её ID.*
     */
    public RecordDto getRecordById(Long id) {
        Record recordEntity = getRecord(id);
        return mapper.toDto(recordEntity);
    }

    /**
     *Метод для обновления существующей записи по её ID.
     */
    @Transactional
    public RecordDto updateRecord(Long id, RecordDto recordRequest) {
        Record Record = getRecord(id);

        recordRepository.save(Record);

        return mapper.toDto(Record);
    }

    /**
     * Метод для удаления записи по её ID.
    */
    public RecordDto deleteRecord(Long id) {
        Record record = getRecord(id);
        recordRepository.deleteById(id);

        return mapper.toDto(record);
    }

    /**
     * Метод для получения записей проекта в указанный временной интервал.
    */
    public List<RecordDto> getRecordsOnProjectBetweenDates(Long projectId, OffsetDateTime startDate, OffsetDateTime endDate) {
        return recordRepository.findByProjectIdAndCreatedAtBetween(projectId, startDate, endDate).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод для получения записей пользователя во всех проектах в указанный временной интервал.
    */
    public List<RecordDto> getUserRecordsAcrossProjectsBetweenDates(Long userId, OffsetDateTime startDate, OffsetDateTime endDate) {
        return recordRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     *Метод для получения всех записей в указанный временной интервал.
     */
    public List<RecordDto> getAllRecordsBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate) {
    return recordRepository.findByCreatedAtBetween(startDate, endDate).stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
}