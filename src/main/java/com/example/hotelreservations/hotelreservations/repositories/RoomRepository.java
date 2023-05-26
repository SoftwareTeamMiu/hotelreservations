package com.example.hotelreservations.hotelreservations.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelreservations.hotelreservations.models.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByRoomTypeSize(int size);
    List<Room> findByRoomTypeLocation(int location);
}
