package com.dsosedov.springldap.controllers;

import com.dsosedov.springldap.models.BazRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/baz")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultErrorAttributes.class)))
})
public class BazController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE, path = "/json")
    @ResponseStatus(HttpStatus.OK)
    public String json(@RequestPart(value = "file", required = false) MultipartFile file, @RequestPart BazRequest json) throws IOException {
        return s(file, json);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE, path = "/str")
    @ResponseStatus(HttpStatus.OK)
    public String str(@RequestPart(value = "file", required = false) MultipartFile file, @RequestPart String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return s(file, mapper.readValue(json, BazRequest.class));
    }

    private String s(MultipartFile file, BazRequest json) throws IOException {
        return new String(file.getBytes()) + json.getI().toString() + "\n" + json.getS() + "\n";
    }

}
