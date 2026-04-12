package com.rauldomingues.payment_system.controller;

import com.rauldomingues.payment_system.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    @GetMapping
    public ResponseEntity createPixEVP() {
        String response = this.pixService.pixCreateEVP();

        return ResponseEntity.ok(response);

    }
}
