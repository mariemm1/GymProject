package com.example.salledesport.repositories;

import com.example.salledesport.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("SELECT c FROM Client c WHERE c.first_name = :firstName AND c.last_name =:lastName")
    Optional<Client> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Client> findByEmail(String email);


}
