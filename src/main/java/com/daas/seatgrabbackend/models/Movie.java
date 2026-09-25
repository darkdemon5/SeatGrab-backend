package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private Long id;

    private String name;
    private String releaseDate;
    private String poster;
    private List<String> cast;
    private List<String> director;
    private List<String> producer;
    private String intro;
    private String genre;
    private String duration;
    private List<String> languages;
    private List<String> ratings;
    private String synopsis;
    private Theater theater;
//    private String theaterId;
}
