package com.example.backend.controllers;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class DashboardController {
    @GetMapping("/dashboardWorker")
    public String dashboardWorker() {
        return "dashboard";
    }

    @GetMapping("/dashboard2Worker")
    public String dashboard2Worker() {
        return "dashboard";
    }

    @GetMapping("/dashboard3Worker")
    public String dashboard3Worker() {
        return "dashboard";
    }

    @GetMapping("/dashboardManager")
    public String dashboardManager() {
        return "dashboard";
    }

    @GetMapping("/dashboard2Manager")
    public String dashboard2Manager() {
        return "dashboard";
    }

    @GetMapping("/dashboard3Manager")
    public String dashboard3Manager() {
        return "dashboard";
    }
    @GetMapping("/profileManager")
    public String profileManager() {
        return "profile";
    }
    @GetMapping("/calendarManager")
    public String calendarManager() {
        return "calendar";
    }

    @GetMapping("/employeesManager")
    public String employeesManager() {
        return "employees";
    }


    @GetMapping("/employees2Manager")
    public String employees2Manager() {
        return "employees2";
    }


    @GetMapping("/employees3Manager")
    public String employees3Manager() {
        return "employees3";
    }

    @GetMapping("/constructionsManager")
    public String constructionsManageraZ() {
        return "constructions";
    }

    @GetMapping("/profileWorker")
    public String profileWorker() {
        return "profile";
    }
    @GetMapping("/calendarWorker")
    public String calendarWorker() {
        return "calendar";
    }

    @GetMapping("/employeesWorker")
    public String employeesWorker() {
        return "employees";
    }


    @GetMapping("/employees2Worker")
    public String employees2Worker() {
        return "employees2";
    }


    @GetMapping("/employees3Worker")
    public String employees3Worker() {
        return "employees3";
    }

    @GetMapping("/constructionsWorker")
    public String constructionsWorker() {
        return "constructions";
    }

}
