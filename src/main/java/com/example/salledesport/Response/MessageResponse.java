package com.example.salledesport.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {

    private String message;

    public MessageResponse(String message){
        this.message = message;
    }
}
