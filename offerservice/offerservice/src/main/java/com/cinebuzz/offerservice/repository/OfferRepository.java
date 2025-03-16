package com.cinebuzz.offerservice.repository;

import com.cinebuzz.offerservice.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByShowId(Long showId);
}
