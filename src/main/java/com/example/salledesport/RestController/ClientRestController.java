package com.example.salledesport.RestController;


import com.example.salledesport.model.Client;
import com.example.salledesport.model.Coach;
import com.example.salledesport.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Client")
public class ClientRestController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Client> getAllClient(){
        return clientService.getAllClient();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }




    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        try {
            // Hash the password before saving
            String hashedPassword = passwordEncoder.encode(client.getPassword());
            client.setPassword(hashedPassword);

            // Save client
            Client createdClient = clientService.createClient(client);

            return ResponseEntity.ok().body("client created successfully with ID: " + createdClient.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating client: " + e.getMessage());
        }
    }

//    @PutMapping
//    public Client editClient(Client client){
//        return clientService.editClient(client);
//    }

    @PutMapping
    public Client editClient(@RequestBody Client client) {
        return clientService.editClient(client);
    }


//    @DeleteMapping("/{id}")
//    public void  deleteClient(@PathVariable Long id){
//        clientService.deleteClientById(id);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.ok("Client deleted successfully");
    }


    @PostMapping("/{clientId}/book/{coursId}")
    public ResponseEntity<String> bookCours(@PathVariable Long clientId, @PathVariable Long coursId) {
        String result = clientService.bookCours(clientId, coursId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    }

