package org.persona.moneyjar.service;

/**
 * @author Satya
 * @created 04/01/2025 - 02:22
 **/

public interface Base64Service {
    String encode(String plainText);
    String decode(String encodedText);
}