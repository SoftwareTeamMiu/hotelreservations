package com.example.hotelreservations.hotelreservations.models;

import jakarta.persistence.*;

@Entity
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "method_name")
    private String method_name;
    @Column(name="method_desciption")
    private String method_description;

    public PaymentMethods() {
    }

    public PaymentMethods(int id, String method_name, String method_description) {
        this.id = id;
        this.method_name = method_name;
        this.method_description = method_description;
    }

    public int getId() { return id; }

    public String getMethod_name() { return method_name; }

    public void setMethod_name(String method_name) { this.method_name = method_name; }

    public String getMethod_description() { return method_description; }

    public void setMethod_description(String method_description) { this.method_description = method_description; }

}
