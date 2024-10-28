package ru.malykhnik.endpointwithnosql.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.malykhnik.endpointwithnosql.entities.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
    Token findByValue(String value);
}
