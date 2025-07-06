package com.example.salledesport.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String modePayment;
    private LocalDate startDate;  // Ensure this is LocalDate, not String
    private LocalDate endDate;    // Ensure this is LocalDate, not String



    @ManyToOne
    @JoinColumn(name = "client_id")
    //@JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModePayment() {
        return modePayment;
    }

    public void setModePayment(String modePayment) {
        this.modePayment = modePayment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    @PrePersist
    public void calculateSubscriptionDates() {
        if (this.startDate == null) {
            this.startDate = LocalDate.now();
        }

        if (this.abonnement != null) {
            this.endDate = this.startDate.plusMonths(this.abonnement.getDurationInMonths());
        }
    }
}
