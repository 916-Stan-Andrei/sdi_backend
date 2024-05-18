package com.example.sdiproject.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "TICKET")
public class Ticket {

    public Ticket() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ticketId")
    private int ticketId;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "EVENT_DATE")
    private String eventDate;

    @Column(name = "PURCHASE_DATE")
    private String purchaseDate;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TICKET_PRIORITY_LEVEl")
    private int ticketPriorityLevel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(int ticketId, String eventName, String eventDate, String purchaseDate, String type, int ticketPriorityLevel, User user, List<Attendee> attendees) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.purchaseDate = purchaseDate;
        this.type = type;
        this.ticketPriorityLevel = ticketPriorityLevel;
        this.user = user;
        this.attendees = attendees;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Attendee> attendees;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + ticketId +
                ", eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", type='" + type + '\'' +
                ", ticketPriorityLevel=" + ticketPriorityLevel +
                ", attendees=" + attendees +
                '}';
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public int getId() {
        return ticketId;
    }

    public void setId(int id) {
        this.ticketId = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTicketPriorityLevel() {
        return ticketPriorityLevel;
    }

    public void setTicketPriorityLevel(int ticketPriorityLevel) {
        this.ticketPriorityLevel = ticketPriorityLevel;
    }




}
