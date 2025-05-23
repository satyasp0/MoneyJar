package org.persona.moneyjar.service.impl;

import lombok.RequiredArgsConstructor;
import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.service.EncryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author Satya
 * @created 04/01/2025 - 02:28
 **/

@Service
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16; // 128-bit authentication tag

    @Value("${encrypt.config.secret}")
    private String secretKey;

    private SecretKey getSecretKey() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(secretKey.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(keyBytes, ALGORITHM);
        } catch (Exception e) {
            throw MoneyJarException.generalError();
        }
    }

    @Override
    public String encrypt(String plainText) {
        return encrypt256(plainText, getSecretKey());
    }

    @Override
    public String decrypt(String encryptedText) {
        return decrypt128(encryptedText, getSecretKey());
    }

    public String encrypt256(String plainText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            byte[] encryptedData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Combine IV + encrypted data
            byte[] encryptedWithIv = new byte[GCM_IV_LENGTH + encryptedData.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, GCM_IV_LENGTH);
            System.arraycopy(encryptedData, 0, encryptedWithIv, GCM_IV_LENGTH, encryptedData.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv);
        } catch (Exception ex) {
            throw MoneyJarException.encryptionError();
        }
    }



    public String decrypt128(String encryptedText, SecretKey key) {
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedText);

            // Extract IV and encrypted data
            byte[] iv = new byte[GCM_IV_LENGTH];
            System.arraycopy(decodedData, 0, iv, 0, GCM_IV_LENGTH);

            byte[] encrypted = new byte[decodedData.length - GCM_IV_LENGTH];
            System.arraycopy(decodedData, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

            byte[] decryptedData = cipher.doFinal(encrypted);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw MoneyJarException.decryptionError();
        }
    }
}
