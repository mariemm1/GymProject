package com.example.salledesport.Controller;


import com.example.salledesport.model.Coach;
import com.example.salledesport.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoachController {
    @Autowired
    CoachService coachService;

    @RequestMapping("/addCoach")
    public String addCoach(Model model) {
        Coach coach = new Coach();
        model.addAttribute("CoachForm", coach);
        return "new_coach";
    }
    @RequestMapping(value = "/saveCoach", method = RequestMethod.POST)
    public String saveCoach(@ModelAttribute("OrderForm") Coach coach, Model model) {
        coachService.createCoach(coach);
        return "redirect:/allCoach";
    }
    @RequestMapping("/allCoach")
    public String listCoach(Model model) {
        List <Coach> listCoach = coachService.getAllCoach();
        model.addAttribute("listCoach", listCoach);
        return "liste_coach";
    }

    @GetMapping("deleteCoach/{id}")
    public String deleteCoach(@PathVariable("id") long id) {
        coachService.deleteCoachById(id);
        return "redirect:/allCoach";
    }
    @GetMapping("editCoach/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model){
        Coach coach = coachService.getCoachById(id);
        model.addAttribute("coach", coach);
        return "update_coach";
    }

    @PostMapping("updateCoach/{id}")
    public String updateProduct(@PathVariable("id") long id, Coach coach){
        coachService.editCoach(coach);
        return "redirect:/allCoach";
    }
}
