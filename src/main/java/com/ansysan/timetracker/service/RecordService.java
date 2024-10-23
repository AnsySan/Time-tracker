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

    public Record getRecord(Long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("Record not found"));
    }

    public Project getProject(Long projectId) {
        return recordRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found")).getProject();
    }

    public User getUser(Long userId) {
        return recordRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Record not found")).getUser();
    }


    public void createRecord(RecordDto recordRequest, Long userId, Long projectId) {
        User userEntity = getUser(userId);
        Project projectEntity = getProject(projectId);

        if (!projectEntity.getUsers().contains(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }

        Record recordEntity = mapper.toEntity(recordRequest);
        recordEntity.setUser(userEntity);
        recordEntity.setProject(projectEntity);
        recordRepository.save(recordEntity);
    }

    public List<RecordDto> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public RecordDto getRecordById(Long id) {
        Record recordEntity = getRecord(id);
        return mapper.toDto(recordEntity);
    }

    @Transactional
    public void updateRecord(Long id, RecordDto recordRequest) {
        RecordDto existingRecordEntity = getRecordById(id);

        existingRecordEntity.getCreatedAt();
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public List<RecordDto> getRecordsOnProjectBetweenDates(Long projectId, OffsetDateTime startDate, OffsetDateTime endDate) {
        return recordRepository.findByProjectIdAndCreatedAtBetween(projectId, startDate, endDate).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RecordDto> getUserRecordsAcrossProjectsBetweenDates(Long userId, OffsetDateTime startDate, OffsetDateTime endDate) {
        return recordRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RecordDto> getAllRecordsBetweenDates(OffsetDateTime startDate, OffsetDateTime endDate) {
        return recordRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}