package org.persona.moneyjar.service.impl;

import org.persona.moneyjar.service.Base64Service;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Satya
 * @created 04/01/2025 - 02:22
 **/
@Service
public class Base64ServiceImpl implements Base64Service {

    @Override
    public String encode(String plainText) {
        // Convert the plaintext to bytes and encode using Base64
        byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    @Override
    public String decode(String encodedText) {
        // Decode the Base64-encoded text to bytes and convert to a string
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}