package com.example.hotelreservations.hotelreservations.models;

import jakarta.persistence.*;

@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "size")
    private int size;
    @Column(name = "location")
    private int location;

    public RoomType() {
    }

    public RoomType(int id, int size, int location) {
        this.id = id;
        this.size = size;
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }
}
