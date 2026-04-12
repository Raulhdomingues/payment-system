package com.rauldomingues.payment_system.controller;

import com.rauldomingues.payment_system.dto.UserRequest;
import com.rauldomingues.payment_system.dto.UserResponse;
import com.rauldomingues.payment_system.entity.User;
import com.rauldomingues.payment_system.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userRequest.toModel();
        UserResponse userSaved = userService.registerUser(user);
        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if(userService.verify(code)) {
            return "Verification successful! You can now log in.";
        } else {
            return "Verification failed! Invalid code or user already verified.";
        }
    }

    @GetMapping("/teste")
    public String teste() {
        return "Você está logado";
    }
}
