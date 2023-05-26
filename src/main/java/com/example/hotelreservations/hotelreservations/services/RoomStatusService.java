package com.example.hotelreservations.hotelreservations.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.RoomStatus;
import com.example.hotelreservations.hotelreservations.repositories.RoomStatusRepository;

@Service
public class RoomStatusService {

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    public List<RoomStatus> getAllRoomStatus() {
        return roomStatusRepository.findAll();
    }

    public RoomStatus getRoomStatusById(int id) {
        Optional<RoomStatus> optionalRoomStatus = roomStatusRepository.findById(id);
        return optionalRoomStatus.orElse(null);
    }

    public RoomStatus createRoomStatus(RoomStatus roomStatus) {
        return roomStatusRepository.save(roomStatus);
    }

    public RoomStatus saveRoomStatus(RoomStatus roomStatus) {
        return roomStatusRepository.save(roomStatus);
    }

    public RoomStatus updateRoomStatus(int id, RoomStatus updatedRoomStatus) {
        Optional<RoomStatus> optionalRoomStatus = roomStatusRepository.findById(id);
        if (optionalRoomStatus.isPresent()) {
            RoomStatus roomStatus = optionalRoomStatus.get();
            roomStatus.setStatus(updatedRoomStatus.getStatus());
            return roomStatusRepository.save(roomStatus);
        } else {
            return null;
        }
    }

    public void deleteRoomStatus(RoomStatus roomStatus) {
        roomStatusRepository.delete(roomStatus);
    }

}
