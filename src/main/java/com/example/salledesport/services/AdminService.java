package com.example.salledesport.services;

import com.example.salledesport.model.Admin;
import com.example.salledesport.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
