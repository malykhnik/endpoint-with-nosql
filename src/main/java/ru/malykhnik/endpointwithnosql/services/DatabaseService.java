package ru.malykhnik.endpointwithnosql.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.malykhnik.endpointwithnosql.dto.CrudStatus;
import ru.malykhnik.endpointwithnosql.repositories.TokenRepository;
import ru.malykhnik.endpointwithnosql.entities.Token;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final TokenRepository repository;

    public CrudStatus checkCRUDAvailability () {
        CrudStatus status = new CrudStatus(true, true, true, true);
        Token token = new Token();
        token.setId("test");
        token.setValue("test value");

        repository.save(token);
        Optional<Token> optionalToken = repository.findById(token.getId());
        Token foundToken = optionalToken.orElse(null);
        if (foundToken == null) {
            status.setCreate(false);
            status.setRead(false);
            status.setUpdate(false);
            status.setDelete(false);
            return status;
        }
        foundToken.setValue("test value1");
        repository.save(foundToken);
        optionalToken = repository.findById(foundToken.getId());
        foundToken = optionalToken.orElse(null);
        if (foundToken == null) {
            status.setUpdate(false);
            foundToken.setValue("test value");
            repository.delete(foundToken);
            if (repository.findById(token.getId()).isPresent()) {
                status.setDelete(false);
            }
            return status;
        } else {
            repository.delete(foundToken);
            if (repository.findById(token.getId()).isPresent()) {
                status.setDelete(false);
            }
            return status;
        }
    }
}
