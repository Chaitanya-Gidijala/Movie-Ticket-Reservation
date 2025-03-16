package com.cinebuzz.offerservice.service;

import com.cinebuzz.offerservice.client.ShowServiceClient;
import com.cinebuzz.offerservice.client.TicketServiceClient;
import com.cinebuzz.offerservice.dto.OfferDto;
import com.cinebuzz.offerservice.entity.Offer;
import com.cinebuzz.offerservice.exception.ResourceNotFoundException;
import com.cinebuzz.offerservice.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ShowServiceClient showServiceClient; // Feign Client for Show Service

    @Autowired
    private TicketServiceClient ticketServiceClient; // Feign Client for Ticket Service

    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        LOGGER.info("Creating new offer for Show ID: {}", offerDto.getShowId());

        if (!showServiceClient.doesShowExist(offerDto.getShowId())) {
            throw new ResourceNotFoundException("Show not found with ID: " + offerDto.getShowId());
        }

        if (!ticketServiceClient.areTicketsAvailable(offerDto.getShowId())) {
            throw new IllegalStateException("No tickets available for Show ID: " + offerDto.getShowId());
        }

        Offer offer = modelMapper.map(offerDto, Offer.class);
        Offer savedOffer = offerRepository.save(offer);

        LOGGER.info("Offer created successfully with ID: {}", savedOffer.getOfferId());
        return modelMapper.map(savedOffer, OfferDto.class);
    }

    @Override
    public OfferDto getOfferById(Long offerId) {
        LOGGER.info("Fetching offer with ID: {}", offerId);

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> {
                    LOGGER.error("Offer not found with ID: {}", offerId);
                    return new ResourceNotFoundException("Offer not found with id: " + offerId);
                });

        return modelMapper.map(offer, OfferDto.class);
    }

    @Override
    public List<OfferDto> getAllOffers() {
        LOGGER.info("Fetching all available offers");

        return offerRepository.findAll().stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OfferDto updateOffer(Long offerId, OfferDto offerDto) {
        LOGGER.info("Updating offer with ID: {}", offerId);

        Offer existingOffer = offerRepository.findById(offerId)
                .orElseThrow(() -> {
                    LOGGER.error("Offer not found with ID: {}", offerId);
                    return new ResourceNotFoundException("Offer not found with id: " + offerId);
                });

        if (!showServiceClient.doesShowExist(offerDto.getShowId())) {
            throw new ResourceNotFoundException("Show not found with ID: " + offerDto.getShowId());
        }

        if (!ticketServiceClient.areTicketsAvailable(offerDto.getShowId())) {
            throw new IllegalStateException("No tickets available for Show ID: " + offerDto.getShowId());
        }

        existingOffer.setShowId(offerDto.getShowId());
        existingOffer.setOfferDetails(offerDto.getOfferDetails());
        existingOffer.setDiscountPercentage(offerDto.getDiscountPercentage());

        Offer updatedOffer = offerRepository.save(existingOffer);
        LOGGER.info("Offer updated successfully for ID: {}", offerId);

        return modelMapper.map(updatedOffer, OfferDto.class);
    }

    @Override
    public void deleteOffer(Long offerId) {
        LOGGER.info("Deleting offer with ID: {}", offerId);

        Offer existingOffer = offerRepository.findById(offerId)
                .orElseThrow(() -> {
                    LOGGER.error("Offer not found with ID: {}", offerId);
                    return new ResourceNotFoundException("Offer not found with id: " + offerId);
                });

        offerRepository.deleteById(offerId);
        LOGGER.info("Offer deleted successfully with ID: {}", offerId);
    }
}
