package com.daas.seatgrabbackend.repo.ownerRepo;

import com.daas.seatgrabbackend.models.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepo extends MongoRepository<Owner, String> {

    Owner getOwnerById(String ownerId);

    boolean existsByEmail(String email);

    Owner getOwnerByEmail(String email);
}
