package com.ansysan.timetracker.mapper;

import com.ansysan.timetracker.dto.UserDto;
import com.ansysan.timetracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", source = "roles")
    UserDto toDto(User user);

    User toEntity(User userDto);

}
