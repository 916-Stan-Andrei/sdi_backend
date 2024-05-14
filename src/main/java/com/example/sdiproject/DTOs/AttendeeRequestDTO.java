package com.example.sdiproject.DTOs;

public record AttendeeRequestDTO(
        String firstName,
        String lastName,
        String birthDate,
        Boolean ticketOwner,
        int ticketId
) {
}
