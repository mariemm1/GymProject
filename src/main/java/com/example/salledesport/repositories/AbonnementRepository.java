package com.example.salledesport.repositories;

import com.example.salledesport.model.Abonnement;
import com.example.salledesport.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {

}
