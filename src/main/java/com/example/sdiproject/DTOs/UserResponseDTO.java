package com.example.sdiproject.DTOs;

import com.example.sdiproject.entities.Role;

public record UserResponseDTO(
        int id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
