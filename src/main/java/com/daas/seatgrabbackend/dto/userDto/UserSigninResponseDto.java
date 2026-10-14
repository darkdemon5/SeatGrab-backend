package com.daas.seatgrabbackend.dto.userDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSigninResponseDto {

    private String id;
    private String name;
    private String email;
    private String dob;
    private String city;
    private List<String> bookingsId;
    private List<String> paymentsId;
    private List<String> ticketsId;
    private boolean isEmailVerified;
    private LocalDateTime createdAt;
}
