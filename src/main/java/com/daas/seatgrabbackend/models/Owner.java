package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    private Long id;

    private String name;
    private String email;
    private String password;
    private String dob;
    private Theater theater;
    private String isEmailVerified;
    private LocalDateTime createdAt;
}
