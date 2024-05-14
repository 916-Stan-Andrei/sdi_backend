package com.example.sdiproject.DTOs;

import com.example.sdiproject.entities.Attendee;

import java.util.List;

public record TicketResponseDTO(
        int ticketId,
        String eventName,
        String eventDate,
        String purchaseDate,
        String type,
        int ticketPriorityLevel,
        List<AttendeeResponseDTO> attendees
) {
}
