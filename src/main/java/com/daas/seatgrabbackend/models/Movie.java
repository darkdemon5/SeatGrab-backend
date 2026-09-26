package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private Long id;

    private String name;
    private LocalDate releaseDate;
    private String poster;
    private List<String> cast;
    private List<String> director;
    private List<String> producer;
    private String intro;
    private String genre;
    private int duration;
    private String ratings;
    private List<String> languages;
    private List<String> reviews;
    private String synopsis;
    private Long theaterId;
    private LocalDateTime createdAt;
}
