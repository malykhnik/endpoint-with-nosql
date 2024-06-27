package ru.s3v3nny.endpointwithnosql.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.s3v3nny.endpointwithnosql.services.EndpointService;
import ru.s3v3nny.endpointwithnosql.dto.Response;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EndpointController {

    private final EndpointService service;

    @GetMapping("/check-status")
    public ResponseEntity checkServiceStatus () {
        Response response = service.checkServiceStatus();
        return response.getError() == null ? ResponseEntity.ok().body(response.getValue())
                : ResponseEntity.badRequest().body(response.getError());
    }
}
