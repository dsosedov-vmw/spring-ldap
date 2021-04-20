package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.models.FooRequest;
import com.dsosedov.springldap.services.FooService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/foo")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                                implementation = DefaultErrorAttributes.class
                        )
                )
        )
})
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

    @Operation(description = "Returns an array of strings")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403")
    })
    @GetMapping
    public String[] get() {
        return fooService.foo();
    }

}
