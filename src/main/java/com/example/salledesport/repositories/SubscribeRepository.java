package com.example.salledesport.repositories;

import com.example.salledesport.model.Client;
import com.example.salledesport.model.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe,Long> {

    List<Subscribe> findByClient(Client client);
}

