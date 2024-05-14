package com.example.sdiproject.services;

import com.example.sdiproject.DTOs.AttendeeRequestDTO;
import com.example.sdiproject.DTOs.AttendeeResponseDTO;
import com.example.sdiproject.entities.Attendee;
import com.example.sdiproject.entities.Ticket;
import com.example.sdiproject.mappers.AttendeeResponseDTOMapper;
import com.example.sdiproject.repositories.AttendeeRepository;
import com.example.sdiproject.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendeeService {
    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AttendeeResponseDTOMapper attendeeResponseDTOMapper;

    public void saveAttendee(AttendeeRequestDTO attendeeRequestDTO){
        if (attendeeRequestDTO == null){
            throw new IllegalArgumentException("Attendee object cannot be null!");
        }

        Attendee attendee = new Attendee();
        attendee.setLastName(attendeeRequestDTO.lastName());
        attendee.setTicketOwner(attendeeRequestDTO.ticketOwner());
        attendee.setFirstName(attendeeRequestDTO.firstName());
        attendee.setBirthDate(attendeeRequestDTO.birthDate());


        Optional<Ticket> ticketOptional = ticketRepository.findById(attendeeRequestDTO.ticketId());
        if (ticketOptional.isPresent()) {
            attendee.setTicket(ticketOptional.get());
            attendeeRepository.save(attendee);
        } else {
            throw new IllegalArgumentException("Ticket with ID " + attendeeRequestDTO.ticketId() + " not found!");
        }
    }

    public List<AttendeeResponseDTO> getAllAttendees(String sortOrder){
        Sort sort;
        if (sortOrder != null && sortOrder.equalsIgnoreCase("asc")){
            sort = Sort.by(Sort.Direction.ASC, "lastName");
        }else if(sortOrder != null && sortOrder.equalsIgnoreCase("desc")){
            sort = Sort.by(Sort.Direction.DESC, "lastName");
        }else {
            return attendeeRepository.findAll().stream().map(attendeeResponseDTOMapper).collect(Collectors.toList());
        }
        return attendeeRepository.findAll(sort).stream().map(attendeeResponseDTOMapper).collect(Collectors.toList());
    }

    public AttendeeResponseDTO getAttendeeById(int id){
        Attendee attendee = attendeeRepository.findById(id).orElse(null);
        if (attendee == null){
            throw new IllegalArgumentException("Attendee with id: " + id + "does not exist!");
        }
        return attendeeResponseDTOMapper.apply(attendee);
    }

    public List<AttendeeResponseDTO> getAllAttendeesByTicketId(int ticketId) {
        return attendeeRepository.findAllAttendeesByTicketId(ticketId)
                .stream().map(attendeeResponseDTOMapper).collect(Collectors.toList());
    }

    public void updateAttendee(Attendee newAttendee){
        Attendee attendeeToUpdate = attendeeRepository.findById(newAttendee.getId()).orElse(null);
        if (attendeeToUpdate == null){
            throw new IllegalArgumentException("Attendee with id: " + newAttendee.getId() + " does not exist!");
        }
        attendeeToUpdate.setBirthDate(newAttendee.getBirthDate());
        attendeeToUpdate.setFirstName(newAttendee.getFirstName());
        attendeeToUpdate.setLastName(newAttendee.getLastName());
        if (newAttendee.getTicketOwner() != null) {
            attendeeToUpdate.setTicketOwner(newAttendee.getTicketOwner());
        }
        attendeeRepository.save(attendeeToUpdate);
    }

    public void deleteAttendeeById(int id){
        Attendee attendee = attendeeRepository.findById(id).orElse(null);
        if (attendee == null){
            throw new IllegalArgumentException("Attendee with id: " + id + "does not exist!");
        }
        attendeeRepository.deleteById(id);
    }
}
