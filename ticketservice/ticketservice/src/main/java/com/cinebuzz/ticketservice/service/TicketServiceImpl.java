package com.cinebuzz.ticketservice.service;

import com.cinebuzz.ticketservice.client.SeatClient;
import com.cinebuzz.ticketservice.client.ShowClient;
import com.cinebuzz.ticketservice.dto.TicketDto;
import com.cinebuzz.ticketservice.entity.Ticket;
import com.cinebuzz.ticketservice.exception.ResourceNotFoundException;
import com.cinebuzz.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowClient showClient;

    @Autowired
    private SeatClient seatClient;

    @Override
    public TicketDto bookTicket(TicketDto ticketDto) {
        // Verify Show and Seat
        showClient.getShowById(ticketDto.getShowId());
        seatClient.getSeatById(ticketDto.getSeatId());

        // Reserve Seat
        seatClient.reserveSeat(ticketDto.getSeatId());

        // Save Ticket
        Ticket ticket = new Ticket(
                ticketDto.getShowId(), ticketDto.getSeatId(),
                ticketDto.getAmount(), LocalDateTime.now(), "CONFIRMED"
        );
        ticketRepository.save(ticket);

        ticketDto.setTicketId(ticket.getTicketId());
        ticketDto.setPurchaseDate(ticket.getPurchaseDate());
        ticketDto.setStatus(ticket.getStatus());
        return ticketDto;
    }

    @Override
    public TicketDto getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        return new TicketDto(ticket.getTicketId(), ticket.getShowId(), ticket.getSeatId(), ticket.getAmount(), ticket.getPurchaseDate(), ticket.getStatus());
    }

    @Override
    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(ticket -> new TicketDto(ticket.getTicketId(), ticket.getShowId(), ticket.getSeatId(), ticket.getAmount(), ticket.getPurchaseDate(), ticket.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public boolean areTicketsAvailable(Long showId) {
        return ticketRepository.countByShowIdAndStatus(showId, "CONFIRMED") > 0;
    }
}
