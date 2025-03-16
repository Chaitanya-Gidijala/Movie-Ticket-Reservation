package com.cinebuzz.seatservice.service;

import com.cinebuzz.seatservice.dto.TierDto;
import com.cinebuzz.seatservice.entity.Tier;
import com.cinebuzz.seatservice.exception.ResourceNotFoundException;
import com.cinebuzz.seatservice.repository.TierRepository;
import com.cinebuzz.seatservice.service.TierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TierServiceImpl implements TierService {
    
    private final TierRepository tierRepository;
    private final ModelMapper modelMapper;

    public TierServiceImpl(TierRepository tierRepository, ModelMapper modelMapper) {
        this.tierRepository = tierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TierDto createTier(TierDto tierDto) {
        Tier tier = modelMapper.map(tierDto, Tier.class);
        Tier savedTier = tierRepository.save(tier);
        return modelMapper.map(savedTier, TierDto.class);
    }

    @Override
    public TierDto getTierById(Long tierId) {
        Tier tier = tierRepository.findById(tierId)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found with id: " + tierId));
        return modelMapper.map(tier, TierDto.class);
    }

    @Override
    public List<TierDto> getAllTiers() {
        List<Tier> tiers = tierRepository.findAll();
        return tiers.stream()
                .map(tier -> modelMapper.map(tier, TierDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TierDto updateTier(Long tierId, TierDto tierDto) {
        Tier existingTier = tierRepository.findById(tierId)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found with id: " + tierId));

        existingTier.setTierName(tierDto.getTierName());
        existingTier.setTheatreId(tierDto.getTheatreId());
        existingTier.setTicketPriceMultiplier(tierDto.getTicketPriceMultiplier());

        Tier updatedTier = tierRepository.save(existingTier);
        return modelMapper.map(updatedTier, TierDto.class);
    }

    @Override
    public void deleteTier(Long tierId) {
        Tier tier = tierRepository.findById(tierId)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found with id: " + tierId));
        tierRepository.delete(tier);
    }
}
