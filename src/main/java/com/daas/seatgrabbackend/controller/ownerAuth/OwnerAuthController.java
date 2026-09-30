package com.daas.seatgrabbackend.controller.ownerAuth;


import com.daas.seatgrabbackend.dto.ownerDto.OwnerInfoDTO;
import com.daas.seatgrabbackend.dto.ownerDto.OwnerSignupRequestDto;
import com.daas.seatgrabbackend.service.ownerService.OwnerAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
public class OwnerAuthController {

    private final OwnerAuthService ownerAuthService;

    public OwnerAuthController(OwnerAuthService ownerAuthService) {
        this.ownerAuthService = ownerAuthService;
    }

    @GetMapping("/me")
    public ResponseEntity<OwnerInfoDTO> getOwner(@RequestBody String ownerId) {
        //Implementing the logic to get the owner details
        return ownerAuthService.getOwner(ownerId);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupOwner(@RequestBody @Valid OwnerSignupRequestDto ownerSignupRequestDto){

        return ownerAuthService.signupOwner(ownerSignupRequestDto);
    }
}
