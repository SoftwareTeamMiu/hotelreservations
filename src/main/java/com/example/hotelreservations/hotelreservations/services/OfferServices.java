package com.example.hotelreservations.hotelreservations.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.Offer;
import com.example.hotelreservations.hotelreservations.repositories.OfferRepository;


@Service
public class OfferServices {
    @Autowired
    private OfferRepository offerRepository;

    public void createOffer(Offer offer) {
        try {
            this.offerRepository.save(offer);
        } catch (Exception e) {
            System.out.println("Error creating offer: " + e.getMessage());
        }
    }

    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    public Offer getOfferById(int offerId) {
        return this.offerRepository.findById(offerId).orElse(null);
    }

    public void deleteOfferById(int offerId) {
        this.offerRepository.deleteById(offerId);
    }
}
