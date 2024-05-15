package com.example.sdiproject.testControllers;

import com.example.sdiproject.DTOs.TicketRequestDTO;
import com.example.sdiproject.DTOs.TicketResponseDTO;
import com.example.sdiproject.api.TicketController;
import com.example.sdiproject.entities.Ticket;
import com.example.sdiproject.services.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @Test
    public void addTicket_ValidTicket_TicketAddedSuccessfully() {
        TicketRequestDTO validTicket = new TicketRequestDTO(
                "Concert",
                "2024-05-15",
                "2024-04-16",
                "General Admission",
                1
        );
        doNothing().when(ticketService).saveTicket(validTicket);

        ResponseEntity<String> response = ticketController.addTicket(validTicket);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Ticket added successfully", response.getBody());
    }

    @Test
    public void addTicket_NullTicket_ReturnsBadRequest() {
        ResponseEntity<String> response = ticketController.addTicket(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getAllTickets_ValidTickets_ReturnsListOfTickets() {
        List<TicketResponseDTO> mockTickets = Arrays.asList(
                new TicketResponseDTO(    1,
                        "Concert",
                        "2024-05-15",
                        "2024-04-16",
                        "General Admission",
                        1,
                        new ArrayList<>()),
                new TicketResponseDTO(    1,
                        "Concert",
                        "2024-05-15",
                        "2024-04-16",
                        "General Admission",
                        1,
                        new ArrayList<>())
        );
        when(ticketService.getAllTickets()).thenReturn(mockTickets);

        ResponseEntity<List<TicketResponseDTO>> response = ticketController.getAllTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTickets, response.getBody());
    }

    @Test
    public void getAllTickets_ServiceThrowsException_ReturnsInternalServerError() {
        when(ticketService.getAllTickets()).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<List<TicketResponseDTO>> response = ticketController.getAllTickets();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void getTicket_ExistingTicket_ReturnsTicket() {
        int ticketId = 123;
        TicketResponseDTO mockTicket = new TicketResponseDTO(    1,
                "Concert",
                "2024-05-15",
                "2024-04-16",
                "General Admission",
                1,
                new ArrayList<>());
        when(ticketService.getTicketById(ticketId)).thenReturn(mockTicket);

        ResponseEntity<TicketResponseDTO> response = ticketController.getTicket(ticketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTicket, response.getBody());
    }

    @Test
    public void getTicket_NonExistingTicket_ReturnsNotFound() {
        int ticketId = 123;
        when(ticketService.getTicketById(ticketId)).thenThrow(new IllegalArgumentException("Ticket not found"));

        ResponseEntity<TicketResponseDTO> response = ticketController.getTicket(ticketId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void updateTicket_ValidTicket_TicketUpdatedSuccessfully() {
        Ticket validTicket = new Ticket(
                1,
                "Concert",
                "2024-05-15",
                "2024-04-16",
                "General Admission",
                1,
                new ArrayList<>());
        doNothing().when(ticketService).updateTicket(validTicket);

        ResponseEntity<String> response = ticketController.updateTicket(validTicket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket updated successfully", response.getBody());
    }

    @Test
    public void updateTicket_InvalidTicket_ReturnsBadRequestWithMessage() {
        Ticket invalidTicket = new Ticket();
        String errorMessage = "Invalid ticket data";
        doThrow(new IllegalArgumentException(errorMessage)).when(ticketService).updateTicket(invalidTicket);

        ResponseEntity<String> response = ticketController.updateTicket(invalidTicket);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void deleteTicket_ExistingTicket_TicketDeletedSuccessfully() {
        int ticketId = 123;
        doNothing().when(ticketService).deleteTicket(ticketId);

        ResponseEntity<String> response = ticketController.deleteTicket(ticketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket deleted successfully", response.getBody());
    }

    @Test
    public void deleteTicket_NonExistingTicket_ReturnsNotFoundWithMessage() {
        int ticketId = 123;
        String errorMessage = "Ticket not found";
        doThrow(new IllegalArgumentException(errorMessage)).when(ticketService).deleteTicket(ticketId);

        ResponseEntity<String> response = ticketController.deleteTicket(ticketId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody());
    }


}
