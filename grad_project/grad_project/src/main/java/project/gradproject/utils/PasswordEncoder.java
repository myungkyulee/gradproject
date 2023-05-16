package project.gradproject.utils;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class PasswordEncoder {
    private final MessageDigest digest;
    private final static String ALGORITHM = "SHA-512";
    private final String salt;

    public PasswordEncoder() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(ALGORITHM);
        UUID uuid = UUID.randomUUID();
        salt = uuid.toString();
    }

    public String encode(String password) {
        if (!StringUtils.hasText(password)) {
            return null;
        }
        password += salt;
        digest.reset();
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        return String.format("%0128x", new BigInteger(1, digest.digest()));
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
