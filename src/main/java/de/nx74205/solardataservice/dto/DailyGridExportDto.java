package de.nx74205.solardataservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyGridExportDto {
    private String date;
    private Double totalExported;
}

