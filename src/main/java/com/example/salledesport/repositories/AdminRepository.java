package com.example.salledesport.repositories;

import com.example.salledesport.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
