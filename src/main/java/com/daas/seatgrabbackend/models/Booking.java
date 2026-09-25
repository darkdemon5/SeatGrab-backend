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
public class Booking {

    @Id
    private Long id;

    private String userId;
    private String showtimeId;
    private List<String> seatBooked;
    private String paymentId;
    private LocalDateTime bookingDate;

}
