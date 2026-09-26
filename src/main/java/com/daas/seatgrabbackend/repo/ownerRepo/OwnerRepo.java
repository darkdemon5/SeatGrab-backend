package com.daas.seatgrabbackend.repo.ownerRepo;

import com.daas.seatgrabbackend.models.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepo extends MongoRepository<Owner, Long> {

    Owner getOwnerById(Long ownerId);
}
