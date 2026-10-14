package com.daas.seatgrabbackend.service.userService;


import com.daas.seatgrabbackend.dto.userDto.*;
import com.daas.seatgrabbackend.models.User;
import com.daas.seatgrabbackend.repo.userRepo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserAuthService(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> getUser(String userId){
        if (!userRepo.existsUserById(userId)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("Message", "User not found"));
        }

        Optional<User> user = userRepo.findUserById(userId);
        User foundUser = user.orElseThrow();

        GetUserResponseDto getUserResponseDto = new GetUserResponseDto(foundUser.getId(), foundUser.getName(),
                foundUser.getEmail(), foundUser.getDob(), foundUser.getCity(), foundUser.getBookingsId(), foundUser.getPaymentsId(),
                foundUser.getTicketsId(), foundUser.isEmailVerified(), foundUser.getCreatedAt());

         return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("Message", "User found", "User data", getUserResponseDto));
    }

    public ResponseEntity<?> signUpUser(UserSignupRequestDto userSignupRequestDto){
        String email = userSignupRequestDto.getEmail();
        if (userRepo.existsUserByEmail(email)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("Message", "Invalid Credentials"));
        }

        User user = new User(UUID.randomUUID().toString(), userSignupRequestDto.getName(), email, passwordEncoder.encode(userSignupRequestDto.getPassword()), userSignupRequestDto.getDob(), userSignupRequestDto.getCity(), userSignupRequestDto.getBookingsId(), userSignupRequestDto.getPaymentsId(), userSignupRequestDto.getTicketsId(), userSignupRequestDto.isEmailVerified(), userSignupRequestDto.getCreatedAt());
        User savedUser = userRepo.save(user);

        UserSignupReponseDto userSignupReponseDto = new UserSignupReponseDto(savedUser.getId(), savedUser.getName(),
                savedUser.getEmail(),savedUser.getDob(),savedUser.getCity(), savedUser.getBookingsId(),savedUser.getPaymentsId(),
                savedUser.getTicketsId(), savedUser.isEmailVerified(), savedUser.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Signed Up!!!", "user data", userSignupReponseDto));
    }

    public ResponseEntity<?> signInUser(UserSigninRequestDto userSigninRequestDto){
        String email = userSigninRequestDto.getEmail();
        if(!userRepo.existsUserByEmail(email)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("Message", "Invalid Credentials"));
        }

        Optional<User> user = userRepo.findUserByEmail(email);
        User foundUser = user.orElseThrow();


        if(passwordEncoder.matches(userSigninRequestDto.getPassword(), foundUser.getPassword())){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("Message", "Invalid Credentials"));
        }
        UserSigninResponseDto userSigninResponseDto = new UserSigninResponseDto(foundUser.getId(), foundUser.getName(),
                foundUser.getEmail(), foundUser.getDob(), foundUser.getCity(), foundUser.getBookingsId(), foundUser.getPaymentsId(),
                foundUser.getTicketsId(), foundUser.isEmailVerified(), foundUser.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Signed In!!!", "user data", userSigninResponseDto));
    }
}
