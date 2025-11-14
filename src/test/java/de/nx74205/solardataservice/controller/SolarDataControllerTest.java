package de.nx74205.solardataservice.controller;

import de.nx74205.solardataservice.dto.DailyChargeDto;
import de.nx74205.solardataservice.dto.DailyDischargeDto;
import de.nx74205.solardataservice.service.SolarDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SolarDataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SolarDataService solarDataService;

    @InjectMocks
    private SolarDataController solarDataController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(solarDataController).build();
    }

    @Test
    public void testGetDailyCharge_HappyPath() throws Exception {
        List<DailyChargeDto> result = List.of(new DailyChargeDto("2025-11-01", 8540.2));
        when(solarDataService.getDailyChargeSums(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(result);

        mockMvc.perform(get("/api/solar/battery/charged").param("date", "2025-11-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2025-11-01"))
                .andExpect(jsonPath("$.totalCharged").value(8540.2));
    }

    @Test
    public void testGetDailyCharge_Empty() throws Exception {
        when(solarDataService.getDailyChargeSums(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/solar/battery/charged").param("date", "2025-11-01"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetDailyDischarge_HappyPath() throws Exception {
        List<DailyDischargeDto> result = List.of(new DailyDischargeDto("2025-11-01", 5420.5));
        when(solarDataService.getDailyDischargeSums(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(result);

        mockMvc.perform(get("/api/solar/battery/discharged").param("date", "2025-11-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2025-11-01"))
                .andExpect(jsonPath("$.totalDischarged").value(5420.5));
    }

    @Test
    public void testGetDailyDischarge_Empty() throws Exception {
        when(solarDataService.getDailyDischargeSums(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/solar/battery/discharged").param("date", "2025-11-01"))
                .andExpect(status().isNotFound());
    }
}
