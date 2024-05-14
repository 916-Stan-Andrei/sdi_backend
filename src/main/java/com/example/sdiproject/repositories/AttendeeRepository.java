package com.example.sdiproject.repositories;

import com.example.sdiproject.entities.Attendee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {

    @Query("SELECT a FROM Attendee a WHERE a.ticket.ticketId = :ticketId")
    List<Attendee> findAllAttendeesByTicketId(int ticketId);

    List<Attendee> findAllByOrderByLastNameAsc();

    List<Attendee> findAllByOrderByLastNameDesc();

    List<Attendee> findAll(Sort sort);
}
