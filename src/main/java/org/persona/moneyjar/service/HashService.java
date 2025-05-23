package org.persona.moneyjar.service;

/**
 * @author Satya
 * @created 04/01/2025 - 02:17
 **/
public interface HashService {
    String hash(String input);
    boolean verify(String originalString, String hashedString);
}
