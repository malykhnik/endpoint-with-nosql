package ru.s3v3nny.endpointwithnosql.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash
public class Token implements Serializable {
    @Id
    String id;
}
