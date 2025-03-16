package com.cinebuzz.seatservice.service;

import com.cinebuzz.seatservice.dto.SeatDto;
import java.util.List;

public interface SeatService {
    SeatDto createSeat(SeatDto seatDto);
    SeatDto getSeatById(Long seatId);
    List<SeatDto> getAllSeats();
    SeatDto updateSeat(Long seatId, SeatDto seatDto);
    void deleteSeat(Long seatId);

    void reserveSeat(Long seatId);
}
