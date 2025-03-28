package com.cinebuzz.seatservice.service;

import com.cinebuzz.seatservice.dto.SeatDto;
import com.cinebuzz.seatservice.entity.Seat;
import com.cinebuzz.seatservice.entity.Tier;
import com.cinebuzz.seatservice.exception.ResourceNotFoundException;
import com.cinebuzz.seatservice.repository.SeatRepository;
import com.cinebuzz.seatservice.repository.TierRepository;
import com.cinebuzz.seatservice.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TierRepository tierRepository;

    @Override
    public SeatDto createSeat(SeatDto seatDto) {
        Tier tier = tierRepository.findById(seatDto.getTierId())
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        Seat seat = new Seat(tier, seatDto.getSeatNumber(), seatDto.getStatus());
        seat = seatRepository.save(seat);

        return new SeatDto(seat.getSeatId(), seat.getTier().getTierId(), seat.getSeatNumber(), seat.getStatus());
    }

    @Override
    public SeatDto getSeatById(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        return new SeatDto(seat.getSeatId(), seat.getTier().getTierId(), seat.getSeatNumber(), seat.getStatus());
    }

    @Override
    public List<SeatDto> getAllSeats() {
        return seatRepository.findAll().stream()
                .map(seat -> new SeatDto(seat.getSeatId(), seat.getTier().getTierId(), seat.getSeatNumber(), seat.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public SeatDto updateSeat(Long seatId, SeatDto seatDto) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setStatus(seatDto.getStatus());

        seat = seatRepository.save(seat);
        return new SeatDto(seat.getSeatId(), seat.getTier().getTierId(), seat.getSeatNumber(), seat.getStatus());
    }

    @Override
    public void deleteSeat(Long seatId) {
        if (!seatRepository.existsById(seatId)) {
            throw new ResourceNotFoundException("Seat not found");
        }
        seatRepository.deleteById(seatId);
    }

    @Override
    public void reserveSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + seatId));

        if ("RESERVED".equalsIgnoreCase(seat.getStatus())) {
            throw new IllegalStateException("Seat is already reserved");
        }

        seat.setStatus("RESERVED");
        seatRepository.save(seat);
    }
}
