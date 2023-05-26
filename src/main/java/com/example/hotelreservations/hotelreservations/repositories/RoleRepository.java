package com.example.hotelreservations.hotelreservations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelreservations.hotelreservations.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findRoleByRoleTitle(String roleTitle);
}
