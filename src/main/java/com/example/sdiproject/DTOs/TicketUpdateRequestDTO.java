package com.example.sdiproject.DTOs;

public record TicketUpdateRequestDTO (
        int ticketId,
        String eventName,
        String eventDate,
        String purchaseDate,
        String type,
        int ticketPriorityLevel,
        int userId
){
}
