package ru.s3v3nny.endpointwithnosql.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.s3v3nny.endpointwithnosql.dto.Message;
import ru.s3v3nny.endpointwithnosql.repositories.TokenRepository;
import ru.s3v3nny.endpointwithnosql.dto.Error;
import ru.s3v3nny.endpointwithnosql.dto.Response;
import ru.s3v3nny.endpointwithnosql.dto.ServiceDto;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EndpointService {

    private final TokenRepository repository;

    public Response<Message, Error> checkServiceStatus() {
        Message msg = new Message(new ArrayList<>());
        ServiceDto serviceDto = new ServiceDto("endpoint", "active");
        msg.services().add(serviceDto);
        try {
            repository.findAll();
            serviceDto = new ServiceDto("sql_database", "active");
            msg.services().add(serviceDto);
        } catch (Exception e) {
            serviceDto = new ServiceDto("nosql_database", "inactive");
            msg.services().add(serviceDto);
        }

        return new Response(msg, null);
    }
}
