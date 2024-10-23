package com.ansysan.timetracker.dto;

import com.ansysan.timetracker.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String username;

    private String password;

    private String email;

    private Role role;
}
