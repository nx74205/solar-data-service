package de.nx74205.solardataservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "item0198")
public class Item0198 {
    
    @Id
    @Column(name = "time")
    private LocalDateTime time;
    
    @Column(name = "value")
    private Double value;
}

