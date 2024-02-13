package ecommerce.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageResponse{
    @JsonProperty("message")
    String message;

    public MessageResponse(String message){
        this.message=message;
    }
}
