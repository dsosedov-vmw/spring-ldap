package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.services.BarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/bar")
public class BarController {

    private final BarService barService;

    public BarController(BarService barService) {
        this.barService = barService;
    }

    @Operation(description = "Returns if the value is even or odd")
    @GetMapping("/{val}")
    public String get(@PathVariable Integer val) {
        Boolean isEven = barService.bar(val);
        return "{\"even\":"+isEven+"}";
    }

}
