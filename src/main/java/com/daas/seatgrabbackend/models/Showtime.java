package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Showtime {

    @Id
    private Long id;

    private int startTime;
    private int totalSeats;
    private int acquiredSeats;
    private int availableSeats;
    private int price;
    private Movie movie;
    private Theater theater;
}
