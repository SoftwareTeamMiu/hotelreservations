package com.example.hotelreservations.hotelreservations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.Reservation;
import com.example.hotelreservations.hotelreservations.models.Room;
import com.example.hotelreservations.hotelreservations.repositories.ReservationRepository;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationByID(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public void deleteReservationByID(int id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getReservationByUserID(String id) {
        return reservationRepository.findByCustomerId(id);
    }

    public boolean hasOverlappingReservations(List<Room> rooms, Date startDate, Date endDate) {
        for (Room room : rooms) {
            List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(room,
                    startDate, endDate);
            if (!overlappingReservations.isEmpty()) {
                return true; // Found overlapping reservations
            }
        }
        return false; // No overlapping reservations
    }
}
