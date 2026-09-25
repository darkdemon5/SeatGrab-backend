package com.daas.seatgrabbackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    private Long id;

    private User user;
    private Long amount;
    private String payMethod;
    private boolean done;
    private LocalDateTime createdAt;
}
