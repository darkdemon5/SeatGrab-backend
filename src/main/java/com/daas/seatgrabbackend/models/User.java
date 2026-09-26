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
public class User {

    @Id
    private Long id;

    private String name;
    private String email;
    private String password;
    private String dob;
    private String city;
    private List<Long> bookingsId;
    private List<Long> paymentsId;
    private List<Long> ticketsId;
    private boolean isEmailVerified;
    private LocalDateTime createdAt;

}
