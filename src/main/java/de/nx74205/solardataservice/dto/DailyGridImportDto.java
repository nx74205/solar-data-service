package de.nx74205.solardataservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyGridImportDto {
    private String date;
    private Double totalImported;
}

