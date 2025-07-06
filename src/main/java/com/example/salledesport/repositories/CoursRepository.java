package com.example.salledesport.repositories;

import com.example.salledesport.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours,Long> {




    List<Cours>findByName(String name);


}
