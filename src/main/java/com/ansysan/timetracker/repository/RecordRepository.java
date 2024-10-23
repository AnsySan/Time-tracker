package com.ansysan.timetracker.repository;

import com.ansysan.timetracker.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUserIdAndCreatedAtBetween(Long userId, OffsetDateTime startDate, OffsetDateTime endDate);

    List<Record> findByCreatedAtBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    List<Record> findByProjectIdAndCreatedAtBetween(Long projectId, OffsetDateTime startDate, OffsetDateTime endDate);
}