package com.example.sdiproject.DTOs;

public record AttendeeResponseDTO(
        int id,
        String firstName,
        String lastName,
        String birthDate,
        Boolean ticketOwner
) {
}
