package com.cinebuzz.seatservice.repository;

import com.cinebuzz.seatservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTier_TierId(Long tierId);
}
