package com.daas.seatgrabbackend.controller.userAuth;


import com.daas.seatgrabbackend.dto.userDto.UserSigninRequestDto;
import com.daas.seatgrabbackend.dto.userDto.UserSignupRequestDto;
import com.daas.seatgrabbackend.service.userService.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService){
        this.userAuthService = userAuthService;
    }


    @GetMapping("/me")
    public ResponseEntity<?> getUser(String userId){

        return userAuthService.getUser(userId);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UserSignupRequestDto userSignupRequestDto){

        return userAuthService.signUpUser(userSignupRequestDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody UserSigninRequestDto userSigninRequestDto){

        return userAuthService.signInUser(userSigninRequestDto);
    }
}
