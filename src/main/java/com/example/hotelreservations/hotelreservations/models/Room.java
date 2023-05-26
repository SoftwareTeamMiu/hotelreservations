package com.example.hotelreservations.hotelreservations.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;

    @ManyToOne(targetEntity = RoomType.class, optional = false)
    @JoinColumn(name = "room_type_id", referencedColumnName = "id", nullable = false)
    private RoomType roomType;

    @ManyToOne(targetEntity = RoomStatus.class, optional = false)
    @JoinColumn(name = "room_status_id", referencedColumnName = "id", nullable = false)
    private RoomStatus roomStatus;

    @ManyToOne(targetEntity = Offer.class, optional = true)
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = true)
    private Offer offer;

    @OneToMany(mappedBy = "room")
    List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Room() {
    }

    public Room(double price, RoomType roomType, RoomStatus roomStatus, Offer offer) {
        this.price = price;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.offer = offer;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

}
