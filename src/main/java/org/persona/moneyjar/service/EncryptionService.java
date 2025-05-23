package org.persona.moneyjar.service;

/**
 * @author Satya
 * @created 04/01/2025 - 02:27
 **/

public interface EncryptionService {
    String encrypt(String plainText);
    String decrypt(String encryptedText);
}