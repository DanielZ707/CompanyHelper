package com.example.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataJson {
    @JsonProperty("sender")
    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}