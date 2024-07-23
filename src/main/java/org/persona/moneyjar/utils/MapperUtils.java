package org.persona.moneyjar.utils;

import org.persona.moneyjar.enums.CardType;
import org.persona.moneyjar.enums.TransactionType;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * @author Satya
 * @created 09/07/2024 - 15:55
 **/
public class MapperUtils {

    private MapperUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void updateField(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.trim().isEmpty()) {
            setter.accept(newValue);
        }
    }

    public static void updateCardType(CardType newValue, Consumer<CardType> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    public static void updateTransactionType(TransactionType newValue, Consumer<TransactionType> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    public static void updateBigDecimal(BigDecimal newValue, Consumer<BigDecimal> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }
}
