package com.example.hotelreservations.hotelreservations.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelreservations.hotelreservations.models.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
}
