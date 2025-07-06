package com.example.salledesport.repositories;

import com.example.salledesport.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach,Long> {


    // Find coach by their first name
    @Query("SELECT c FROM Coach c WHERE c.first_name = :firstName")
    Optional<Coach> findByFirstName(@Param("firstName") String firstName);
}
