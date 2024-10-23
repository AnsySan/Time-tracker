package com.ansysan.timetracker.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {

    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";
}