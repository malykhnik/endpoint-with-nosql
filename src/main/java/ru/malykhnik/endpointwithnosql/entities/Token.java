package ru.malykhnik.endpointwithnosql.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@RedisHash
public class Token implements Serializable {
    @Id
    @Indexed
    String id;

    String value;
}
