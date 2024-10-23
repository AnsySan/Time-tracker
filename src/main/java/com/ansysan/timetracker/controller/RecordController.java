package com.ansysan.timetracker.controller;

import com.ansysan.timetracker.dto.RecordDto;
import com.ansysan.timetracker.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/projects/{projectId}/users/{userId}")
    @PreAuthorize("hasRole('ROLE_User')")
    public void createRecord(@PathVariable Long userId,
                             @PathVariable Long projectId,
                             @RequestBody RecordDto recordRequest
    ) {
        recordService.createRecord(recordRequest, userId, projectId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_Admin')")
    public List<RecordDto> getAllRecords() {
        return recordService.getAllRecords();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_User')")
    public RecordDto getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_User')")
    public void updateRecord(@PathVariable Long id,
                             @RequestBody RecordDto recordRequest
    ) {
        recordService.updateRecord(id, recordRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_User')")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }

    @GetMapping("/projects/{projectId}/between-dates")
    @PreAuthorize("hasRole('ROLE_User')")
    public List<RecordDto> getRecordsOnProjectBetweenDates(@PathVariable Long projectId,
                                                                @RequestParam OffsetDateTime startDate,
                                                                @RequestParam OffsetDateTime endDate) {
        return recordService.getRecordsOnProjectBetweenDates(projectId, startDate, endDate);
    }

    @GetMapping("/users/{userId}/between-dates")
    @PreAuthorize("hasRole('ROLE_User')")
    public List<RecordDto> getUserRecordsAcrossProjectsBetweenDates(@PathVariable Long userId,
                                                                         @RequestParam OffsetDateTime startDate,
                                                                         @RequestParam OffsetDateTime endDate) {
        return recordService.getUserRecordsAcrossProjectsBetweenDates(userId, startDate, endDate);
    }

    @GetMapping("/between-dates")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public List<RecordDto> getAllRecordsBetweenDates(@RequestParam OffsetDateTime startDate,
                                                          @RequestParam OffsetDateTime endDate) {
        return recordService.getAllRecordsBetweenDates(startDate, endDate);
    }
}