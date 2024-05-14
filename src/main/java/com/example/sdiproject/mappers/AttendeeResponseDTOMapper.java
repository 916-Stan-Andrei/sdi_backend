package com.example.sdiproject.mappers;

import com.example.sdiproject.DTOs.AttendeeResponseDTO;
import com.example.sdiproject.entities.Attendee;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AttendeeResponseDTOMapper implements Function<Attendee, AttendeeResponseDTO> {

    @Override
    public AttendeeResponseDTO apply(Attendee attendee) {
        return new AttendeeResponseDTO(
                attendee.getId(),
                attendee.getFirstName(),
                attendee.getLastName(),
                attendee.getBirthDate(),
                attendee.getTicketOwner()
        );
    }
}
