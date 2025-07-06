package com.example.salledesport.RestController;

import com.example.salledesport.model.Subscribe;
import com.example.salledesport.services.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscribeRestController {


    @Autowired
    private SubscribeService subscribeService;

    // Create a new subscription
//    @PostMapping("/addSubscribe")
//    public ResponseEntity<Subscribe> addSubscribe(@RequestBody Subscribe subscribe) {
//        try {
//            Subscribe createdSubscribe = subscribeService.AddSubscribe(subscribe);
//            return new ResponseEntity<>(createdSubscribe, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/addSubscribe")
    public ResponseEntity<?> addSubscribe(@RequestBody Subscribe subscribe) {
        try {
            Subscribe created = subscribeService.AddSubscribe(subscribe);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    // Get all subscriptions
    @GetMapping("/allSubscribes")
    public ResponseEntity<List<Subscribe>> getAllSubscribes() {
        List<Subscribe> subscribes = subscribeService.getAllSubscribes();
        return new ResponseEntity<>(subscribes, HttpStatus.OK);
    }
}

