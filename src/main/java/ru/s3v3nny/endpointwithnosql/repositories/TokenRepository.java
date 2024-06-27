package ru.s3v3nny.endpointwithnosql.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.s3v3nny.endpointwithnosql.entities.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
}
