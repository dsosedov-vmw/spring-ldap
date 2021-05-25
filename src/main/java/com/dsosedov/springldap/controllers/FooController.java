package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.models.FooRequest;
import com.dsosedov.springldap.services.FooService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/foo")
public class FooController {

    private final FooService fooService;

    public FooController(FooService fooService) {
        this.fooService = fooService;
    }

    @Operation(description = "Creates a string")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "500")
    })
    @PostMapping
    public void post(@RequestBody FooRequest request) {
        fooService.add(request.getVal());
    }

    @Operation(description = "Returns an array of strings", security = @SecurityRequirement(name = "KeyCloak"))
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403")
    })
    @GetMapping
    public String[] get() {
        return fooService.foo();
    }

    @Operation(description = "Creates a string")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "500")
    })
    @PutMapping("/{val}")
    public String put(@PathVariable String val, @RequestBody FooRequest request) {
        return val + " changed to " + request.getVal();
    }

}
