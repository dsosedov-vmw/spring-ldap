package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.services.BarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bar")
public class BarController {

    private final BarService barService;

    public BarController(BarService barService) {
        this.barService = barService;
    }

    @GetMapping("/{val}")
    public String get(@PathVariable Integer val) {
        Boolean isEven = barService.bar(val);
        return "{\"even\":"+isEven+"}";
    }

}
