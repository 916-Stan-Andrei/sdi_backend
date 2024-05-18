package com.example.sdiproject.mappers;

import com.example.sdiproject.DTOs.AttendeeResponseDTO;
import com.example.sdiproject.DTOs.TicketResponseDTO;
import com.example.sdiproject.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketResponseDTOMapper implements Function<Ticket, TicketResponseDTO> {

    @Autowired
    private AttendeeResponseDTOMapper attendeeResponseDTOMapper;

    @Override
    public TicketResponseDTO apply(Ticket ticket) {
        List<AttendeeResponseDTO> attendeeResponseDTOS = ticket.getAttendees()
                .stream()
                .map(attendeeResponseDTOMapper).toList();

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getEventName(),
                ticket.getPurchaseDate(),
                ticket.getEventDate(),
                ticket.getType(),
                ticket.getTicketPriorityLevel(),
                attendeeResponseDTOS,
                ticket.getUser().getId()
        );
    }
}
