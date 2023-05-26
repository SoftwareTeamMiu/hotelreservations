package com.example.hotelreservations.hotelreservations.models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Role title is required")
    @Column(unique = true)
    private String roleTitle;

    public Role(int id, String roleTitle) {
        this.id = id;
        this.roleTitle = roleTitle;
    }

    public Role() {

    }

    public int getId() {
        return this.id;
    }

    public String getroleTitle() {
        return this.roleTitle;
    }

    public void setroleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", roleTitle='" + getroleTitle() + "'" + "}";
    }

    @Override
    public String getAuthority() {
        return this.roleTitle;
    }

}
