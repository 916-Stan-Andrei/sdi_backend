package com.example.sdiproject.api;

import com.example.sdiproject.DTOs.AuthenticationResponse;
import com.example.sdiproject.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;

    @GetMapping("/id")
    public ResponseEntity<Integer> getUserIdFromToken(@RequestBody AuthenticationResponse token){
        return ResponseEntity.ok(jwtService.extractUserId(token.getToken()));
    }

    @GetMapping("/role")
    public ResponseEntity<String> getRoleFromToken(@RequestBody AuthenticationResponse token){
        return ResponseEntity.ok(jwtService.extractRole(token.getToken()));
    }
}
