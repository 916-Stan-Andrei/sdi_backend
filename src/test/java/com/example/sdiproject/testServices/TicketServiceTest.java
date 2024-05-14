package com.example.sdiproject.testServices;

import com.example.sdiproject.DTOs.AttendeeResponseDTO;
import com.example.sdiproject.DTOs.TicketRequestDTO;
import com.example.sdiproject.DTOs.TicketResponseDTO;
import com.example.sdiproject.entities.Ticket;
import com.example.sdiproject.mappers.TicketResponseDTOMapper;
import com.example.sdiproject.repositories.TicketRepository;
import com.example.sdiproject.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketResponseDTOMapper ticketResponseDTOMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTicket_ValidRequestDTO_TicketSavedSuccessfully() {
        TicketRequestDTO requestDTO = new TicketRequestDTO("Event", "2024-12-31", "2024-04-21", "Type", 1);

        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket savedTicket = invocation.getArgument(0);
            savedTicket.setId(1);
            return savedTicket;
        });

        assertDoesNotThrow(() -> ticketService.saveTicket(requestDTO));
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void saveTicket_NullRequestDTO_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ticketService.saveTicket(null));

        assertEquals("Ticket object cannot be null", exception.getMessage());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void getAllTickets_ReturnListOfTickets_Success() {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2);
        List<Ticket> ticketList = Arrays.asList(ticket1, ticket2);

        AttendeeResponseDTO attendee1 = new AttendeeResponseDTO(
                1,
                "John",
                "Doe",
                "1990-01-01",
                true
        );

        AttendeeResponseDTO attendee2 = new AttendeeResponseDTO(
                2,
                "Jane",
                "Smith",
                "1985-05-15",
                false
        );

        TicketResponseDTO ticketResponseDTO1 = new TicketResponseDTO(
                1,
                "Event 1",
                "2024-04-25",
                "2024-04-24",
                "Type 1",
                1,
                List.of(attendee1)
        );

        TicketResponseDTO ticketResponseDTO2 = new TicketResponseDTO(
                2,
                "Event 2",
                "2024-04-26",
                "2024-04-24",
                "Type 2",
                2,
                List.of(attendee2)
        );

        List<TicketResponseDTO> expectedResponseDTOList = Arrays.asList(ticketResponseDTO1, ticketResponseDTO2);

        // Mocking repository behavior
        when(ticketRepository.findAll()).thenReturn(ticketList);

        // Mocking mapper behavior
        when(ticketResponseDTOMapper.apply(ticket1)).thenReturn(ticketResponseDTO1);
        when(ticketResponseDTOMapper.apply(ticket2)).thenReturn(ticketResponseDTO2);

        // When
        List<TicketResponseDTO> actualResponseDTOList = ticketService.getAllTickets();

        // Then
        assertEquals(expectedResponseDTOList.size(), actualResponseDTOList.size());
        assertTrue(actualResponseDTOList.containsAll(expectedResponseDTOList));
        verify(ticketRepository, times(1)).findAll();
        verify(ticketResponseDTOMapper, times(2)).apply(any(Ticket.class));
    }


    @Test
    void getAllTickets_NoTickets_ReturnEmptyList() {
        List<Ticket> tickets = new ArrayList<>();
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<TicketResponseDTO> result = ticketService.getAllTickets();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ticketRepository, times(1)).findAll();
        verify(ticketResponseDTOMapper, never()).apply(any(Ticket.class));
    }

    @Test
    void getTicketById_ValidId_ReturnTicketSuccessfully(){
        int ticketId = 1;

        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setEventName("Test Event");
        ticket.setEventDate("2024-04-25");
        ticket.setPurchaseDate("2024-04-24");
        ticket.setType("Type 1");
        ticket.setTicketPriorityLevel(1);

        AttendeeResponseDTO attendee1 = new AttendeeResponseDTO(
                1,
                "John",
                "Doe",
                "1990-01-01",
                true
        );


        TicketResponseDTO expectedResponseDTO = new TicketResponseDTO(
                ticket.getId(),
                ticket.getEventName(),
                ticket.getEventDate(),
                ticket.getPurchaseDate(),
                ticket.getType(),
                ticket.getTicketPriorityLevel(),
                List.of(attendee1)
        );

        // Mocking repository behavior
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        // Mocking mapper behavior
        when(ticketResponseDTOMapper.apply(ticket)).thenReturn(expectedResponseDTO);

        // When
        TicketResponseDTO actualResponseDTO = ticketService.getTicketById(ticketId);

        // Then
        assertEquals(expectedResponseDTO, actualResponseDTO);
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketResponseDTOMapper, times(1)).apply(ticket);
    }

    @Test
    void getTicketById_InvalidId_ReturnTicketSuccessfully_throwsException(){
        int ticketId = 1;

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ticketService.getTicketById(ticketId));

        assertEquals("Ticket with ID " + ticketId + " not found", exception.getMessage());
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketResponseDTOMapper, never()).apply(any(Ticket.class));
    }

    @Test
    void updateTicket_ValidTicketToUpdate_UpdatesSuccessfully(){
        Ticket updatedTicket = new Ticket();
        updatedTicket.setId(1);
        updatedTicket.setEventName("New Event");
        updatedTicket.setEventDate("2024-05-01");
        updatedTicket.setPurchaseDate("2024-04-30");
        updatedTicket.setType("New Type");
        updatedTicket.setTicketPriorityLevel(2);

        when(ticketRepository.findById(updatedTicket.getId())).thenReturn(Optional.of(updatedTicket));

        assertDoesNotThrow(() -> ticketService.updateTicket(updatedTicket));

        verify(ticketRepository, times(1)).findById(updatedTicket.getId());
        verify(ticketRepository, times(1)).save(updatedTicket);
        assertEquals("New Event", updatedTicket.getEventName());
        assertEquals("2024-05-01", updatedTicket.getEventDate());
        assertEquals("2024-04-30", updatedTicket.getPurchaseDate());
        assertEquals("New Type", updatedTicket.getType());
        assertEquals(2, updatedTicket.getTicketPriorityLevel());
    }

    @Test
    void updateTicket_updateNonexistentTicket_throwsError(){
        Ticket updatedTicket = new Ticket();
        updatedTicket.setId(1);

        when(ticketRepository.findById(updatedTicket.getId())).thenReturn(java.util.Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ticketService.updateTicket(updatedTicket));

        assertEquals("Ticket with ID " + updatedTicket.getId() + " not found", exception.getMessage());
        verify(ticketRepository, times(1)).findById(updatedTicket.getId());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    public void testDeleteTicket_ExistingTicket_DeletesSuccessfully() {
        int ticketId = 1;

        when(ticketRepository.existsById(ticketId)).thenReturn(true);

        assertDoesNotThrow(() -> ticketService.deleteTicket(ticketId));

        verify(ticketRepository, times(1)).existsById(ticketId);
        verify(ticketRepository, times(1)).deleteById(ticketId);
    }

    @Test
    public void testDeleteTicket_NonexistentTicket_ThrowsException() {
        int ticketId = 1;

        when(ticketRepository.existsById(ticketId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ticketService.deleteTicket(ticketId));

        assertEquals("Ticket with ID " + ticketId + " not found", exception.getMessage());
        verify(ticketRepository, times(1)).existsById(ticketId);
        verify(ticketRepository, never()).deleteById(ticketId);
    }
}


