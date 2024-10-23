package com.ansysan.timetracker.mapper;

import com.ansysan.timetracker.dto.RecordDto;
import com.ansysan.timetracker.entity.Record;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface RecordMapper {

    RecordDto toDto(Record record);

    Record toEntity(RecordDto recordDto);

}