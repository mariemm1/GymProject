package com.example.salledesport.Controller;

import com.example.salledesport.model.Abonnement;
import com.example.salledesport.model.Subscribe;
import com.example.salledesport.services.AbonnementService;
import com.example.salledesport.services.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
@Controller

public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private AbonnementService abonnementService;



    // Show the form to add a subscription for a specific Abonnement
    @GetMapping("/new/{abonnementId}")
    public String showNewSubscriptionForm(@PathVariable Long abonnementId, Model model) {
        Abonnement abonnement = abonnementService.getAbonnementById(abonnementId);
        model.addAttribute("abonnement", abonnement);
        model.addAttribute("subscribe", new Subscribe());
        return "new_subscribe"; // The page for adding a new subscription
    }

    // Save the subscription and redirect to My Subscriptions
    @PostMapping("/addSubscribe")
    public String addSubscription(@RequestParam Long abonnementId,
                                  @RequestParam String modePayment,
                                  @RequestParam String startDate) {
        Subscribe subscribe = new Subscribe();
        subscribe.setAbonnement(abonnementService.getAbonnementById(abonnementId));
        subscribe.setModePayment(modePayment);
        subscribe.setStartDate(LocalDate.parse(startDate)); // Parse the start date

        // Save the subscription via service
        subscribeService.addSubscribe2(subscribe);

        return "redirect:/my_subscriptions"; // Redirect to My Subscriptions page
    }

    // This method will retrieve subscriptions for the logged-in client (or just all subscriptions)
    @GetMapping("/my_subscriptions")
    public String showSubscriptions(Model model) {
        List<Subscribe> subscriptions = subscribeService.getAllSubscribes(); // Fetch subscriptions from the service
        model.addAttribute("subscriptions", subscriptions); // Add the subscriptions list to the model
        return "my_subscriptions"; // Return the view name (Thymeleaf template)
    }

    // View the details of a specific subscription
    @GetMapping("/{id}")
    public String viewSubscription(@PathVariable Long id, Model model) {
        Subscribe subscription = subscribeService.getSubscribeById(id);
        model.addAttribute("subscription", subscription);
        return "subscribe"; // Display the subscription details
    }

    // Add the method for deleting a subscription
    @PostMapping("/delete/{id}")
    public String deleteSubscription(@PathVariable Long id) {
        // Call the service method to delete the subscription
        subscribeService.deleteSubscribe(id);
        return "redirect:/my_subscriptions"; // Redirect to "My Subscriptions" after deletion
    }







}

