package com.daas.seatgrabbackend.dto.ownerDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerInfoDTO {

    private String name;
    private String email;
    private String dob;
    private Long theaterId;
    private LocalDateTime createdAt;
}
