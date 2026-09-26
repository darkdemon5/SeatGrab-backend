package com.daas.seatgrabbackend.models;


import com.daas.seatgrabbackend.enums.PaymentMethod;
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
public class Payment {

    @Id
    private Long id;

    private String userId;
    private BigDecimal amount;
    private PaymentMethod payMethod;
    private boolean done;
    private LocalDateTime createdAt;
}
