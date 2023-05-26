package com.example.hotelreservations.hotelreservations.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelreservations.hotelreservations.models.Role;
import com.example.hotelreservations.hotelreservations.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public String deleteRole(int id) {
        roleRepository.deleteById(id);
        return "Role with id " + id + " deleted";
    }

    public Role findRoleByRoleTitle(String roleTitle) {
        return roleRepository.findRoleByRoleTitle(roleTitle);
    }
}
