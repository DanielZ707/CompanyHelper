package com.example.backend.controllers;


import com.example.backend.models.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
    public class MessageListener {
        @Autowired
        SimpMessagingTemplate template;

        @KafkaListener(
                topics = "first_kafka_topic",
                groupId = "group1"
        )
        public void listen(Message message) {
            System.out.println("sending via kafka listener..");
            template.convertAndSend("/topic/group", message);
        }
    }

