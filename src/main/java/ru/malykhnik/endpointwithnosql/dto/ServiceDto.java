package ru.malykhnik.endpointwithnosql.dto;

public record ServiceDto(String name, String status, CrudStatus crud_status) { }
