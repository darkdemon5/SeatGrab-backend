package com.daas.seatgrabbackend.repo.userRepo;

import com.daas.seatgrabbackend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    boolean existsUserById(String id);

    Optional<User> findUserById(String id);
    Optional<User> findUserByEmail(String email);
    boolean existsUserByEmail(String email);
}
