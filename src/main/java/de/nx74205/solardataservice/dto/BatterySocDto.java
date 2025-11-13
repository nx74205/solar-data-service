package de.nx74205.solardataservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BatterySocDto {
    private LocalDateTime timestamp;
    private Integer percent;
}

