package com.example.hotelreservations.hotelreservations.services;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.RoomType;
import com.example.hotelreservations.hotelreservations.repositories.RoomTypeRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public RoomType getRoomTypeById(Integer id) {
        return roomTypeRepository.findById(id).orElse(null);
    }

    public RoomType createRoomType(RoomType RoomType) {
        return roomTypeRepository.save(RoomType);
    }

    public void deleteRoomType(Integer id) {
        roomTypeRepository.deleteById(id);
    }

    public List<Integer> getRoomTypesSizes() {
        List<Integer> roomsSizes = roomTypeRepository.findAll().stream().map(RoomType::getSize)
                .distinct().sorted()
                .collect(Collectors.toList());
        return roomsSizes;
    }

    public List<Integer> getRoomTypesLocations() {
        List<Integer> roomsLocations = roomTypeRepository.findAll().stream().map(RoomType::getLocation)
                .distinct().sorted()
                .collect(Collectors.toList());
        return roomsLocations;
    }
}
