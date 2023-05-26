package com.example.hotelreservations.hotelreservations.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Africa/Cairo")
    private Date start_date;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Africa/Cairo")
    private Date end_date;

    @Column(name = "total_price")
    private double total_price;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User customer;

    @ManyToMany
    @JoinTable(name = "reservation_rooms", joinColumns = @JoinColumn(name = "reservation_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;

    @ManyToOne(targetEntity = PaymentMethods.class, optional = false)
    @JoinColumn(name = "paymentMethods_id", referencedColumnName = "id", nullable = false)
    private PaymentMethods paymentMethods;

    @ManyToOne(targetEntity = ReservationStatus.class, optional = false)
    @JoinColumn(name = "reservationStatus_id", referencedColumnName = "id", nullable = false)
    private ReservationStatus reservationStatus;

    public Reservation() {
    }

    public Reservation(int id, Date start_date, Date end_date, double total_price, User customer, List<Room> rooms,
            PaymentMethods paymentMethods, ReservationStatus reservationStatus) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_price = total_price;
        this.customer = customer;
        this.rooms = rooms;
        this.paymentMethods = paymentMethods;
        this.reservationStatus = reservationStatus;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getId() {
        return id;
    }
}
