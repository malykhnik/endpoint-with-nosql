package ru.s3v3nny.endpointwithnosql.dto;

import java.util.List;

public record Message (String token, List<ServiceDto> services) { }
