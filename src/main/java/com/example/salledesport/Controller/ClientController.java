package com.example.salledesport.Controller;


import com.example.salledesport.model.Client;
import com.example.salledesport.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {
    @Autowired
    ClientService clientService;


    @RequestMapping("/addClient")
    public String addClient(Model model) {
        Client client = new Client();
        model.addAttribute("ClientForm", client);
        return "new_client";
    }
    @RequestMapping(value = "/saveClient", method = RequestMethod.POST)
    public String saveClient(@ModelAttribute("OrderForm") Client client, Model model) {
        clientService.createClient(client);
        return "redirect:/allClient";
    }
    @RequestMapping("/allClient")
    public String listClient(Model model) {
        List <Client> listClient = clientService.getAllClient();
        model.addAttribute("listClient", listClient);
        return "liste_client";
    }

    @GetMapping("deleteClient/{id}")
    public String deleteClient(@PathVariable("id") long id) {
        clientService.deleteClientById(id);
        return "redirect:/allClient";
    }
    @GetMapping("editClient/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model){
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "update_client";
    }

    @PostMapping("updateClient/{id}")
    public String updateProduct(@PathVariable("id") long id, Client client){
        clientService.editClient(client);
        return "redirect:/allClient";
    }
}