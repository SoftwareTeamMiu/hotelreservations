package com.example.hotelreservations.hotelreservations.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.Room;
import com.example.hotelreservations.hotelreservations.repositories.RoomRepository;

@Service
public class RoomServices {
    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(Room room) {
        roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void deleteRoomById(int id) {
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsBySize(int size) {
        return roomRepository.findByRoomTypeSize(size);
    }

    public List<Room> getRoomsByLocation(int location) {
        return roomRepository.findByRoomTypeLocation(location);
    }

}
