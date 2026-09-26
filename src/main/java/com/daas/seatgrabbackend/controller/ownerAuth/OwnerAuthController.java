package com.daas.seatgrabbackend.controller.ownerAuth;


import com.daas.seatgrabbackend.dto.ownerDto.OwnerInfoDTO;
import com.daas.seatgrabbackend.service.ownerService.OwnerAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner")
public class OwnerAuthController {

    OwnerAuthService ownerAuthService;

    public OwnerAuthController(OwnerAuthService ownerAuthService) {
        this.ownerAuthService = ownerAuthService;
    }

    @GetMapping("/me")
    public ResponseEntity<OwnerInfoDTO> getOwner(@RequestBody Long ownerId) {
        //Implementing the logic to get the owner details

        return ownerAuthService.getOwner(ownerId);


    }
}
