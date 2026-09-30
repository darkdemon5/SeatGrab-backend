package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Showtime {

    @Id
    private String id;

    private LocalDateTime startTime;
    private int totalSeats;
    private int acquiredSeats;
    private int availableSeats;
    private BigDecimal price;
    private String movieId;
    private String theaterId;
}
