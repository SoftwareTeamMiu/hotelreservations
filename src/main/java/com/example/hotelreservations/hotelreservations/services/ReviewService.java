package com.example.hotelreservations.hotelreservations.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.Review;
import com.example.hotelreservations.hotelreservations.models.Room;
import com.example.hotelreservations.hotelreservations.repositories.ReviewRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoomServices roomServices;

    @Autowired
    Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    List<String> errorMessages = new ArrayList<>();

    public ResponseEntity createReview(Map<String, Object> body, String token) {
        try {
            Review review = new Review();
            review.setUser(userService.getUserByToken(token));
            review.setReview_title((String) body.get("review_title"));
            review.setReview_description((String) body.get("review_description"));
            Room room = new Room();
            String room_id = (String) body.get("room_id");
            room = roomServices.getRoomById(Integer.parseInt(room_id));
            review.setRoom(room);
            if (validateReview(review)) {
                return new ResponseEntity<List<String>>(errorMessages, HttpStatus.BAD_REQUEST);
            } else {
                reviewRepository.save(review);
                return new ResponseEntity<>("Review Created", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Error Creating Review: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getAllReviews() {
        try {
            return new ResponseEntity<>(reviewRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error getting Reviews: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getReviewById(int reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if (review != null) {
                return ResponseEntity.ok(review);
            } else {
                return new ResponseEntity<String>("Review not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Error getting Review: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateReviewById(Map<String, Object> body, String token, int reviewId) {
        try {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if (review != null) {
                review.setUser(userService.getUserByToken(token));
                if (body.containsKey("review_title")) {
                    review.setReview_title((String) body.get("review_title"));
                }
                if (body.containsKey("review_description")) {
                    review.setReview_description((String) body.get("review_description"));
                }
                reviewRepository.save(review);
                return new ResponseEntity<>("Review Updated", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("Review not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Error Updating Review: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteReviewById(int reviewId){
        try {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if (review != null) {
                reviewRepository.delete(review);
                return ResponseEntity.ok("Review deleted");
            } else {
                return new ResponseEntity<String>("Review not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Error deleting Review: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public boolean validateReview(Review review) {
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        if (!violations.isEmpty()) {
            errorMessages.clear();
            for (ConstraintViolation<Review> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }
}
