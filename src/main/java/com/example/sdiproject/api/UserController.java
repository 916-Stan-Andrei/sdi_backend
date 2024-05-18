package com.example.sdiproject.api;

import com.example.sdiproject.DTOs.AuthenticationResponse;
import com.example.sdiproject.DTOs.UserResponseDTO;
import com.example.sdiproject.entities.Role;
import com.example.sdiproject.entities.User;
import com.example.sdiproject.services.JwtService;
import com.example.sdiproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;

    private final UserService userService;

    @GetMapping("/id")
    public ResponseEntity<Integer> getUserIdFromToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(jwtService.extractUserId(jwtToken));
    }

    @GetMapping("/role")
    public ResponseEntity<String> getRoleFromToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(jwtService.extractRole(jwtToken));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestParam(required = false) Role role){
        return ResponseEntity.ok(userService.getAllUsers(role));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        try {
            UserResponseDTO user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        try {
            UserResponseDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
