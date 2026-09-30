package com.daas.seatgrabbackend.dto.ownerDto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerSignupRequestDto {

    private String name;

    @Email(message = "Email should be valid")
    private String email;

    private String password;
    private String dob;
}
