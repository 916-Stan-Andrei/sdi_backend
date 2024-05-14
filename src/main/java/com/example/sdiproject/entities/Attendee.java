package com.example.sdiproject.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "ATTENDEE")
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String birthDate;

    @Column
    private Boolean ticketOwner;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;


    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", isTicketOwner=" + ticketOwner +
                ", ticket=" + ticket +
                '}';
    }

    public Attendee(int id, String firstName, String lastName, String birthDate, Boolean ticketOwner, Ticket ticket) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.ticketOwner = ticketOwner;
        this.ticket = ticket;
    }
    public Attendee() {
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getTicketOwner() {
        return this.ticketOwner;
    }

    public void setTicketOwner(Boolean ticketOwner) {
        this.ticketOwner = ticketOwner;
    }
}
