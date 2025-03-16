package com.cinebuzz.seatservice.service;

import com.cinebuzz.seatservice.dto.TierDto;
import java.util.List;

public interface TierService {
    TierDto createTier(TierDto tierDto);
    TierDto getTierById(Long tierId);
    List<TierDto> getAllTiers();
    TierDto updateTier(Long tierId, TierDto tierDto);
    void deleteTier(Long tierId);
}
