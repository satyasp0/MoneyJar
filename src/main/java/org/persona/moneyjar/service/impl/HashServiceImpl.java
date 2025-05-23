package org.persona.moneyjar.service.impl;

import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.service.HashService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Satya
 * @created 04/01/2025 - 02:18
 **/
@Service
public class HashServiceImpl implements HashService {
    @Override
    public String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw MoneyJarException.generalError();
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean verify(String originalString, String hashedString) {
        String generatedHash = hash(originalString);
        return generatedHash.equals(hashedString);
    }
}
