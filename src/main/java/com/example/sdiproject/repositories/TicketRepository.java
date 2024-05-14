package com.example.sdiproject.repositories;

import com.example.sdiproject.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Modifying
    @Query("delete from Ticket t where t.ticketId in ?1")
    void deleteTicketsWithIds(List<Integer> ids);

    List<Ticket> findAllByUserId(int userId);
}
