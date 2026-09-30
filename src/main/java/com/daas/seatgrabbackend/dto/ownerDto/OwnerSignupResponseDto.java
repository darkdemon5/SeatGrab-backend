package com.daas.seatgrabbackend.dto.ownerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerSignupResponseDto {

    private String id;
    private String name;
    private String email;
    private String dob;
    private boolean isEmailVerified;
    private LocalDateTime createdAt;
}
