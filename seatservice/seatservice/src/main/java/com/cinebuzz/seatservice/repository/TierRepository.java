package com.cinebuzz.seatservice.repository;

import com.cinebuzz.seatservice.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TierRepository extends JpaRepository<Tier, Long> {
    List<Tier> findByTheatreId(Long theatreId);
}
