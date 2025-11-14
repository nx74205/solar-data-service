package de.nx74205.solardataservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailySumsDto {
    private String entityName;
    private String date;
    private Double value;
}
