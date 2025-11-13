package de.nx74205.solardataservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HourlySummaryDto {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("ac_out")
    private Double acOut;

    @JsonProperty("battery_out")
    private Double batteryOut;

    @JsonProperty("battery_in")
    private Double batteryIn;
}

