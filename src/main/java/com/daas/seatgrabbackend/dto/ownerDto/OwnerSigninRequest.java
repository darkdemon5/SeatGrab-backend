package com.daas.seatgrabbackend.dto.ownerDto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerSigninRequest {

    @Email(message = "Please provide a valid email")
    private String email;

    private String password;
}
