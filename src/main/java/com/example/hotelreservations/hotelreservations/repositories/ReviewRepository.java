package com.example.hotelreservations.hotelreservations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelreservations.hotelreservations.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    
}
