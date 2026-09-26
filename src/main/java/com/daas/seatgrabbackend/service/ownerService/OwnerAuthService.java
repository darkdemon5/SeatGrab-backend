package com.daas.seatgrabbackend.service.ownerService;

import com.daas.seatgrabbackend.dto.ownerDto.OwnerInfoDTO;
import com.daas.seatgrabbackend.models.Owner;
import com.daas.seatgrabbackend.repo.ownerRepo.OwnerRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OwnerAuthService {

    private final OwnerRepo ownerRepo;

    public OwnerAuthService(OwnerRepo ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    public ResponseEntity<OwnerInfoDTO> getOwner(Long ownerId) {
        //Implementing the logic to get the owner details
        Owner owner = ownerRepo.getOwnerById(ownerId);
        if(owner == null){
            return ResponseEntity.notFound().build();
        }

        OwnerInfoDTO ownerInfoDTO = new OwnerInfoDTO(owner.getName(), owner.getEmail(), owner.getDob(), owner.getTheaterId(), owner.getCreatedAt());

      return ResponseEntity.ok(ownerInfoDTO);

    }
}
