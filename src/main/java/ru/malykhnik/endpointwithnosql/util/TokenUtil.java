package ru.malykhnik.endpointwithnosql.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenUtil {
    public String generateToken() {
        byte[] bytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }
}
