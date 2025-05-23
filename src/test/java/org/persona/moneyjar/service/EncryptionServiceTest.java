package org.persona.moneyjar.service;

import org.junit.jupiter.api.Test;
import org.persona.moneyjar.service.impl.EncryptionServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Satya
 * @created 04/01/2025 - 02:51
 **/
class EncryptionServiceTest {

    @Test
    void encrypt() {
        EncryptionService encryptionService = new EncryptionServiceImpl();
        String data = "testData";
        String encryptedData = encryptionService.encrypt(data);
        assertNotEquals("testData", encryptedData, "The data should be encrypted (reversed in this case)");
    }
}