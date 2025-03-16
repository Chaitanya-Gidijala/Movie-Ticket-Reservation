package com.cinebuzz.offerservice.service;

import com.cinebuzz.offerservice.dto.OfferDto;
import java.util.List;

public interface OfferService {
    OfferDto createOffer(OfferDto offerDto);
    OfferDto getOfferById(Long offerId);
    List<OfferDto> getAllOffers();
    OfferDto updateOffer(Long offerId, OfferDto offerDto);
    void deleteOffer(Long offerId);
}
