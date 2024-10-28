package ru.malykhnik.endpointwithnosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrudStatus {
    Boolean create;
    Boolean read;
    Boolean update;
    Boolean delete;
}
