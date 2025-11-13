package de.nx74205.solardataservice.service;

import de.nx74205.solardataservice.entity.Item0197;
import de.nx74205.solardataservice.entity.Item0198;
import de.nx74205.solardataservice.repository.Item0197Repository;
import de.nx74205.solardataservice.repository.Item0198Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolarDataService {

    private final Item0197Repository item0197Repository;
    private final Item0198Repository item0198Repository;

    /**
     * Get the latest value from item0197
     */
    public Item0197 getLatestItem0197() {
        return item0197Repository.findTopByOrderByTimeDesc();
    }

    /**
     * Get the latest value from item0198
     */
    public Item0198 getLatestItem0198() {
        return item0198Repository.findTopByOrderByTimeDesc();
    }

    /**
     * Get all item0197 entries in a time range
     */
    public List<Item0197> getItem0197InRange(LocalDateTime start, LocalDateTime end) {
        return item0197Repository.findByTimeBetween(start, end);
    }

    /**
     * Get all item0198 entries in a time range
     */
    public List<Item0198> getItem0198InRange(LocalDateTime start, LocalDateTime end) {
        return item0198Repository.findByTimeBetween(start, end);
    }

    /**
     * Get average value for item0197 in a time range
     */
    public Double getAverageItem0197(LocalDateTime start, LocalDateTime end) {
        return item0197Repository.getAverageValueBetween(start, end);
    }

    /**
     * Get average value for item0198 in a time range
     */
    public Double getAverageItem0198(LocalDateTime start, LocalDateTime end) {
        return item0198Repository.getAverageValueBetween(start, end);
    }
}

