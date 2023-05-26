package com.example.hotelreservations.hotelreservations.services;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.ReservationStatus;
import com.example.hotelreservations.hotelreservations.repositories.ReservationStatusRepository;

@Service
public class ReservationStatusService {

    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    public List<ReservationStatus> getAllReservationStatus() {
        return reservationStatusRepository.findAll();
    }

    public ReservationStatus getReservationStatusById(Integer id) {
        Optional<ReservationStatus> reservationStatus = reservationStatusRepository.findById(id);
        return reservationStatus.orElse(null);
    }

    public ReservationStatus addReservationStatus(ReservationStatus reservationStatus) {
        return reservationStatusRepository.save(reservationStatus);
    }

    public ReservationStatus updateReservationStatus(Integer id, ReservationStatus reservationStatus) {
        Optional<ReservationStatus> existingReservationStatus = reservationStatusRepository.findById(id);
        return existingReservationStatus.map(res -> {
            res.setStatus(reservationStatus.getStatus());
            return reservationStatusRepository.save(res);
        }).orElse(null);
    }

    public void deleteReservationStatus(Integer id) {
        Optional<ReservationStatus> existingReservationStatus = reservationStatusRepository.findById(id);
        existingReservationStatus.ifPresent(reservationStatusRepository::delete);
    }
}
