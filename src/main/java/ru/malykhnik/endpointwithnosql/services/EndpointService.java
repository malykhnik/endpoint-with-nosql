package ru.malykhnik.endpointwithnosql.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.malykhnik.endpointwithnosql.dto.*;
import ru.malykhnik.endpointwithnosql.dto.Error;
import ru.malykhnik.endpointwithnosql.entities.Token;
import ru.malykhnik.endpointwithnosql.repositories.TokenRepository;
import ru.malykhnik.endpointwithnosql.util.TokenUtil;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EndpointService {
    private final Logger LOGGER = LoggerFactory.getLogger(EndpointService.class);

    private final TokenRepository repository;
    private final TokenUtil util;
    private final DatabaseService databaseService;

    @Value("${auth.login}")
    String login;
    @Value("${auth.password_hash}")
    String passwordHash;

    private boolean database_online;
    private String cachedToken;

    public Response<Message, Error> checkServiceStatus(String token) {
        if(!database_online && token.equals(cachedToken)) {
            ArrayList<ServiceDto> serviceDtos = new ArrayList<>();
            ServiceDto serviceDto = new ServiceDto("endpoint", "active", null);
            serviceDtos.add(serviceDto);
            serviceDto = new ServiceDto("nosql_database", "inactive", null);
            String newToken = util.generateToken();
            cachedToken = newToken;
            serviceDtos.add(serviceDto);
            var msg = new Message(newToken, serviceDtos);
            LOGGER.info("ЗАШЛО В if" + msg);
            return new Response(msg, null);
        } else if (!token.equals(cachedToken) && cachedToken != null) {
            var err = new Error("Wrong token");
            LOGGER.error("WRONG TOKEN_1");
            return new Response<>(null, err);
        }

        if (!validateToken(token)) {
            var err = new Error("Wrong token");
            LOGGER.error("WRONG TOKEN_2");
            return new Response<>(null, err);
        }
        ArrayList<ServiceDto> serviceDtos = new ArrayList<ServiceDto>();
        ServiceDto serviceDto = new ServiceDto("endpoint", "active", null);
        serviceDtos.add(serviceDto);
        repository.findAll();
        serviceDto = new ServiceDto("nosql_database", "active", databaseService.checkCRUDAvailability());
        serviceDtos.add(serviceDto);
        String newToken = util.generateToken();
        Optional<Token> optionalToken = repository.findById("endpoint_token");
        Token tokenObj = optionalToken.orElse(null);
        tokenObj.setValue(newToken);
        repository.save(tokenObj);
        var msg = new Message(newToken, serviceDtos);
        return new Response(msg, null);
    }

    public boolean validateToken(String token) {
        return repository.findAll().iterator().next().getValue().equals(token);
    }

    public Response getToken(AuthData auth) {
        if (!validateAuth(auth)) {
            var err = new Error("Wrong auth data");
            return new Response<>(null, err);
        }
        String newToken = util.generateToken();
        try {
            Optional<Token> optionalToken = repository.findById("endpoint_token");
            Token token = optionalToken.orElse(null);
            if(token == null) {
                token = new Token();
                token.setValue(newToken);
                token.setId("endpoint_token");
                repository.save(token);
            }
            token.setValue(newToken);
            repository.save(token);
            database_online = true;
        } catch (Exception e) {
            database_online = false;
            cachedToken = newToken;
        }
        var tokenDto = new TokenData(newToken);
        return new Response<>(tokenDto, null);
    }

    public boolean validateAuth(AuthData auth) {
        return auth.login().equals(this.login) && auth.password_hash().equals(this.passwordHash);
    }
}
