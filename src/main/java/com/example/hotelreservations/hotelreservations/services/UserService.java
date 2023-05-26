package com.example.hotelreservations.hotelreservations.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.User;
import com.example.hotelreservations.hotelreservations.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByToken(String token){
        String Userid = jwtService.extractUUID(token);
        User user = this.getUserById(Userid);
        return user;
    }

    public String extractConstraintName(Exception e) {
        String message = e.getMessage();
        int startIndex = message.indexOf("constraint [") + 12;
        int endIndex = message.indexOf("]", startIndex);
        if (startIndex != -1 && endIndex != -1) {
            return message.substring(startIndex, endIndex);
        }
        return null;
    }
}
