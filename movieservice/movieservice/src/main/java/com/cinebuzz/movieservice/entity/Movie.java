package com.cinebuzz.movieservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    
    @Column(nullable = false)
    private String movieName;
    
    @Column(nullable = false)
    private String genre; //The term "genre" refers to the category or type of a movie based on its style, theme, or content.
    
    @Column(nullable = false)
    private int duration; // Duration in minutes
    
    @Column(nullable = false)
    private double ticketBasePrice;

    // Constructors
    public Movie() {}

    public Movie(Long movieId, String movieName, String genre, int duration, double ticketBasePrice) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.duration = duration;
        this.ticketBasePrice = ticketBasePrice;
    }

    // Getters and Setters
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getTicketBasePrice() { return ticketBasePrice; }
    public void setTicketBasePrice(double ticketBasePrice) { this.ticketBasePrice = ticketBasePrice; }
}
