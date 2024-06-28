package ru.s3v3nny.endpointwithnosql.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.s3v3nny.endpointwithnosql.dto.AuthData;
import ru.s3v3nny.endpointwithnosql.dto.TokenData;
import ru.s3v3nny.endpointwithnosql.services.EndpointService;
import ru.s3v3nny.endpointwithnosql.dto.Response;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EndpointController {

    private final EndpointService service;

    @GetMapping("/check-status")
    public ResponseEntity checkServiceStatus (@RequestBody TokenData tokenData) {
        Response response = service.checkServiceStatus(tokenData);
        return response.getError() == null ? ResponseEntity.ok().body(response.getValue())
                : ResponseEntity.badRequest().body(response.getError());
    }

    @PostMapping("/get-token")
    public ResponseEntity getToken (@RequestBody AuthData authData) {
        Response response = service.getToken(authData);
        return response.getError() == null ? ResponseEntity.ok().body(response.getValue())
                : ResponseEntity.badRequest().body(response.getError());
    }
}
