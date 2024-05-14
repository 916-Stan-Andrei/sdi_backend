package com.example.sdiproject.testServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.sdiproject.DTOs.AttendeeResponseDTO;
import com.example.sdiproject.mappers.AttendeeResponseDTOMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.sdiproject.DTOs.AttendeeRequestDTO;
import com.example.sdiproject.entities.Attendee;
import com.example.sdiproject.entities.Ticket;
import com.example.sdiproject.repositories.AttendeeRepository;
import com.example.sdiproject.repositories.TicketRepository;
import com.example.sdiproject.services.AttendeeService;
import org.springframework.data.domain.Sort;

public class AttendeeServiceTest {

    @Mock
    private AttendeeRepository attendeeRepository;

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private AttendeeResponseDTOMapper attendeeResponseDTOMapper;


    @InjectMocks
    private AttendeeService attendeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAttendee_ValidInput_SavesSuccessfully() {
        AttendeeRequestDTO attendeeRequestDTO = new AttendeeRequestDTO(
                "John",
                "Doe",
                "1990-01-01",
                true,
                1
        );

        Ticket ticket = new Ticket();
        ticket.setId(1);

        when(ticketRepository.findById(attendeeRequestDTO.ticketId())).thenReturn(Optional.of(ticket));

        assertDoesNotThrow(() -> attendeeService.saveAttendee(attendeeRequestDTO));

        verify(ticketRepository, times(1)).findById(attendeeRequestDTO.ticketId());
        verify(attendeeRepository, times(1)).save(any(Attendee.class));
    }

    @Test
    public void testSaveAttendee_NullRequestDTO_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> attendeeService.saveAttendee(null));

        assertEquals("Attendee object cannot be null!", exception.getMessage());
        verify(ticketRepository, never()).findById(anyInt());
        verify(attendeeRepository, never()).save(any(Attendee.class));
    }

    @Test
    public void testSaveAttendee_InvalidTicketId_ThrowsException() {
        AttendeeRequestDTO attendeeRequestDTO = new AttendeeRequestDTO(
                "John",
                "Doe",
                "1990-01-01",
                true,
                1
        );

        when(ticketRepository.findById(attendeeRequestDTO.ticketId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> attendeeService.saveAttendee(attendeeRequestDTO));

        assertEquals("Ticket with ID " + attendeeRequestDTO.ticketId() + " not found!", exception.getMessage());
        verify(ticketRepository, times(1)).findById(attendeeRequestDTO.ticketId());
        verify(attendeeRepository, never()).save(any(Attendee.class));
    }

    @Test
    public void testGetAllAttendees_AscendingOrder_success() {
        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee(1, "John", "Doe", "1990-01-01", true, null));
        attendees.add(new Attendee(2, "Alice", "Smith", "1992-05-15", false, null));

        when(attendeeRepository.findAll(Sort.by(Sort.Direction.ASC, "lastName"))).thenReturn(attendees);
        when(attendeeResponseDTOMapper.apply(any())).thenAnswer(invocation -> {
            Attendee attendee = invocation.getArgument(0);
            return new AttendeeResponseDTO(attendee.getId(), attendee.getFirstName(), attendee.getLastName(),
                    attendee.getBirthDate(), attendee.getTicketOwner());
        });

        List<AttendeeResponseDTO> result = attendeeService.getAllAttendees("asc");

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Doe", result.get(0).lastName());
        Assertions.assertEquals("Smith", result.get(1).lastName());
    }

    @Test
    public void testGetAllAttendeesDescendingOrder() {
        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee(1, "John", "Doe", "1990-01-01", true, null));
        attendees.add(new Attendee(2, "Alice", "Smith", "1992-05-15", false, null));

        when(attendeeRepository.findAll(Sort.by(Sort.Direction.DESC, "lastName"))).thenReturn(attendees);
        when(attendeeResponseDTOMapper.apply(any())).thenAnswer(invocation -> {
            Attendee attendee = invocation.getArgument(0);
            return new AttendeeResponseDTO(attendee.getId(), attendee.getFirstName(), attendee.getLastName(),
                    attendee.getBirthDate(), attendee.getTicketOwner());
        });

        List<AttendeeResponseDTO> result = attendeeService.getAllAttendees("desc");

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Smith", result.get(0).lastName());
        Assertions.assertEquals("Doe", result.get(1).lastName());
    }

    @Test
    public void testGetAllAttendeesWithNullSortOrder() {
        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee(1, "John", "Doe", "1990-01-01", true, null));
        attendees.add(new Attendee(2, "Alice", "Smith", "1992-05-15", false, null));

        when(attendeeRepository.findAll()).thenReturn(attendees);
        when(attendeeResponseDTOMapper.apply(any())).thenAnswer(invocation -> {
            Attendee attendee = invocation.getArgument(0);
            return new AttendeeResponseDTO(attendee.getId(), attendee.getFirstName(), attendee.getLastName(),
                    attendee.getBirthDate(), attendee.getTicketOwner());
        });

        List<AttendeeResponseDTO> result = attendeeService.getAllAttendees(null);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Doe", result.get(0).lastName());
        Assertions.assertEquals("Smith", result.get(1).lastName());
    }


    @Test
    public void testGetAttendeeByIdWhenNotExists() {
        when(attendeeRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            attendeeService.getAttendeeById(1);
        });
    }
}
