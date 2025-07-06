package com.example.salledesport.Controller;


import com.example.salledesport.model.Cours;
import com.example.salledesport.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoursController {
    @Autowired
    CoursService coursService;
    @RequestMapping("/addCours")
    public String addCours(Model model) {
        Cours cours = new Cours();
        model.addAttribute("CoursForm", cours);
        return "new_cours";
    }
    @RequestMapping(value = "/savecours", method = RequestMethod.POST)
    public String saveCours(@ModelAttribute("CoursForm") Cours cours) {
        coursService.addCoursWithCoach(cours);
        return "redirect:/allCours";
    }
    @RequestMapping("/allCours")
    public String listCours(Model model) {
        List<Cours> listCours = coursService.getAllCours();
        model.addAttribute("listCours", listCours);
        return "liste_cours";}
    @GetMapping("deleteCours/{id}")
    public String deleteCours(@PathVariable("id") Long id) {
        coursService.deleteCours(id);
        return "redirect:/allCours";
    }
    @GetMapping("editCours/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Cours cours = coursService.getCoursById(id);
        model.addAttribute("cours", cours);
        return "update_cours";
    }

    @PostMapping("updateCours/{id}")
    public String updateCours(@PathVariable("id") Long id, Cours cours){
        coursService.updateCours(cours);
        return "redirect:/allCours";
    }
}
