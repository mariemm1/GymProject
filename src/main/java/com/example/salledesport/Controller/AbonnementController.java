package com.example.salledesport.Controller;

import com.example.salledesport.model.Abonnement;
import com.example.salledesport.services.AbonnementService;
import com.example.salledesport.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller

public class AbonnementController {

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private CoursService coursService;

    // Display form to add a new Abonnement

    @RequestMapping("/addAbonnement")
    public String addAbonnement(Model model) {
        Abonnement abonnement = new Abonnement();
        model.addAttribute("abonnementForm", abonnement);
        model.addAttribute("coursList", coursService.getAllCours());
        return "new_abonnement"; // Ensure this corresponds to the .html file in your templates
    }

    @RequestMapping(value = "/saveAbonnement", method = RequestMethod.POST)
    public String saveAbonnement(@ModelAttribute("abonnementForm") Abonnement abonnement) {
        abonnementService.createAbonnement(abonnement);
        return "redirect:/allAbonnement"; // Redirect to the correct endpoint
    }

    // List all Abonnements
    @RequestMapping("/allAbonnement")
    public String listAbonnements(Model model) {
        List<Abonnement> abonnements = abonnementService.getAllAbonnements();
        model.addAttribute("abonnements", abonnements);
        return "list_abonnement";
    }

    // Delete Abonnement
    @GetMapping("/deleteAbonnement/{id}")
    public String deleteAbonnement(@PathVariable("id") Long id) {
        abonnementService.deleteAbonnement(id);
        return "redirect:/allAbonnement";
    }

    // Show update form for Abonnement
    @GetMapping("/editAbonnement/{id}")
    public String updateAbonnement(@PathVariable("id") Long id, Model model) {
        Abonnement abonnement = abonnementService.getAbonnementById(id);
        model.addAttribute("abonnementForm", abonnement);
        model.addAttribute("coursList", coursService.getAllCours());
        return "update_abonnement";
    }

    // Update Abonnement
    @PostMapping("/updateAbonnement/{id}")
    public String updateAbonnement(@PathVariable("id") Long id, @ModelAttribute("abonnementForm") Abonnement abonnement) {
        abonnementService.updateAbonnement(id,abonnement);
        return "redirect:/allAbonnement";
    }
}

