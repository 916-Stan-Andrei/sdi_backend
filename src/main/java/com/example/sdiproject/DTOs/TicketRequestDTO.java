package com.example.sdiproject.DTOs;

public record TicketRequestDTO(
        String eventName,
        String eventDate,
        String purchaseDate,
        String type,
        int ticketPriorityLevel,
        int userId
) {
}
