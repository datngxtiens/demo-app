package com.demo.demo.controller;

import com.demo.demo.response.HealthyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health/ping")
    public ResponseEntity<HealthyResponse> checkHealth() {
        HealthyResponse response = HealthyResponse.builder().code("000")
                .message("Successful").detail("Welcome to Gettting Started with Jenkins Lab.").build();
        return ResponseEntity.ok(response);
    }
}
