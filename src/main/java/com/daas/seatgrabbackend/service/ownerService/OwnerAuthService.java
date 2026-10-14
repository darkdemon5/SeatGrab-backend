package com.daas.seatgrabbackend.service.ownerService;

import com.daas.seatgrabbackend.dto.ownerDto.*;
import com.daas.seatgrabbackend.models.Owner;
import com.daas.seatgrabbackend.repo.ownerRepo.OwnerRepo;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class OwnerAuthService {

    private final OwnerRepo ownerRepo;
    private final PasswordEncoder passwordEncoder;

    public OwnerAuthService(OwnerRepo ownerRepo, PasswordEncoder passwordEncoder) {
        this.ownerRepo = ownerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<OwnerInfoDTO> getOwner(String ownerId) {
        //Implementing the logic to get the owner details
        Owner owner = ownerRepo.getOwnerById(ownerId);
        if(owner == null){
            return ResponseEntity.notFound().build();
        }

        OwnerInfoDTO ownerInfoDTO;
        try{
            ownerInfoDTO= new OwnerInfoDTO(owner.getName(), owner.getEmail(), owner.getDob(), owner.getTheaterId(), owner.getCreatedAt());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

      return ResponseEntity.ok(ownerInfoDTO);

    }

    public ResponseEntity<?> signupOwner(@NonNull @Valid OwnerSignupRequestDto ownerSignupRequestDto) {

        //Implementing sign up for owner
        String email = ownerSignupRequestDto.getEmail().trim().toLowerCase(Locale.ROOT);
        if(ownerRepo.existsByEmail(email)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email exists!!!"));
        }
        OwnerSignupResponseDto responseDto;
       try{

           Owner owner = new Owner();
           owner.setId(UUID.randomUUID().toString());
           owner.setName(ownerSignupRequestDto.getName());
           owner.setEmail(email);
           owner.setPassword(passwordEncoder.encode(ownerSignupRequestDto.getPassword()));
           owner.setDob(ownerSignupRequestDto.getDob());
           owner.setEmailVerified(false);
           owner.setCreatedAt(LocalDateTime.now());
           Owner savedOwner = ownerRepo.save(owner);

           responseDto = new OwnerSignupResponseDto(savedOwner.getId(), savedOwner.getName(), savedOwner.getEmail(), savedOwner.getDob(),savedOwner.isEmailVerified(),savedOwner.getCreatedAt());
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Owner Signed Up!!!", "owner data", responseDto));
    }

    public ResponseEntity<?> signinOwner(@NonNull OwnerSigninRequest ownerSigninRequest){
        //Implementing sign in for owner
        String email = ownerSigninRequest.getEmail().trim().toLowerCase(Locale.ROOT);

        Owner owner = ownerRepo.getOwnerByEmail(email);
        if(!ownerRepo.existsByEmail(email)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Invalid credentials!!!"));
        }
        else{

            if (!passwordEncoder.matches(ownerSigninRequest.getPassword(), owner.getPassword())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Invalid credentials!!!"));
            }
        }
        owner = ownerRepo.getOwnerByEmail(email);
        OwnerSigninResponse ownerSigninResponse = new OwnerSigninResponse(owner.getId(), owner.getName(), owner.getEmail(), owner.getDob(), owner.isEmailVerified(), owner.getCreatedAt());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "You are signed in", "Ownerdata", ownerSigninResponse));
    }
}
