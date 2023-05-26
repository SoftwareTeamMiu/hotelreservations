package com.example.hotelreservations.hotelreservations.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hotelreservations.hotelreservations.models.Reservation;
import com.example.hotelreservations.hotelreservations.models.Room;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
       List<Reservation> findByCustomerId(String id);

       @Query("SELECT r FROM Reservation r JOIN r.rooms room " +
                     "WHERE room = :room " +
                     "AND r.start_date <= :endDate " +
                     "AND r.end_date >= :startDate")
       List<Reservation> findOverlappingReservations(@Param("room") Room room,
                     @Param("startDate") Date startDate,
                     @Param("endDate") Date endDate);
}
