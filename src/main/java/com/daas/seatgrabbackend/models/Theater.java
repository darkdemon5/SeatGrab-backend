package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theater {

    @Id
    private Long id;

    private String name;
    private String address;
    private int nosSeats;
    private Owner owner;
    private List<Movie> movies;
    private LocalDateTime createdAt;
}
