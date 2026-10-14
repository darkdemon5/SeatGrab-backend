package com.daas.seatgrabbackend.dto.userDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSigninRequestDto {

    private String email;
    private String password;
}
