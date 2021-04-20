package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.services.BarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bar")
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
public class BarController {

    private final BarService barService;

    public BarController(BarService barService) {
        this.barService = barService;
    }

    @Operation(
            description = "Returns if the value is even or odd",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = String.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = ""
                    )
            }
    )
    @GetMapping("/{val}")
    public String get(@PathVariable Integer val) {
        Boolean isEven = barService.bar(val);
        return "{\"even\":"+isEven+"}";
    }

}
