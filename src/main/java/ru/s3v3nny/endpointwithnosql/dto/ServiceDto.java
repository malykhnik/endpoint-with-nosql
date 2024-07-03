package ru.s3v3nny.endpointwithnosql.dto;

public record ServiceDto(String name, String status, CrudStatus crud_status) { }
