package com.cinebuzz.seatservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tier")
public class Tier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tierId;

    @Column(nullable = false, unique = true)
    private String tierName; // e.g., VIP, STANDARD, ECONOMY

    @Column(nullable = false)
    private Long theatreId; // Linked to Theatre Service

    @Column(nullable = false)
    private double ticketPriceMultiplier; // Multiplier for ticket price

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    public Tier() {}

    public Tier(String tierName, Long theatreId, double ticketPriceMultiplier) {
        this.tierName = tierName;
        this.theatreId = theatreId;
        this.ticketPriceMultiplier = ticketPriceMultiplier;
    }

    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long tierId) {
        this.tierId = tierId;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    public double getTicketPriceMultiplier() {
        return ticketPriceMultiplier;
    }

    public void setTicketPriceMultiplier(double ticketPriceMultiplier) {
        this.ticketPriceMultiplier = ticketPriceMultiplier;
    }
}
