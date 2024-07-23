package org.persona.moneyjar.utils;

import org.persona.moneyjar.enums.TransactionType;

import java.math.BigDecimal;

/**
 * @author Satya
 * @created 10/07/2024 - 16:25
 **/
public class TransactionUtils {
    private TransactionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static BigDecimal calculateTransaction(BigDecimal current, BigDecimal amount, TransactionType type) {
        if (type.equals(TransactionType.INCOME)){
            return current.add(amount);
        }else {
            return current.subtract(amount);
        }
    }
}
