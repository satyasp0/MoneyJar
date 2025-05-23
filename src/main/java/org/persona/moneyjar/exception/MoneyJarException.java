package org.persona.moneyjar.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 01:18
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class MoneyJarException extends RuntimeException {
    private final String appCode;
    private final String errorCode;
    private static final String MYJ = "MYJ";

    public MoneyJarException(String appCode, String errorCode, String message) {
        super(message);
        this.appCode = appCode;
        this.errorCode = errorCode;
    }

    public static MoneyJarException generalError() {
        return new MoneyJarException(MYJ, "ERR-00", "Please Contact The Developer");
    }
    public static MoneyJarException decryptionError() {
        return new MoneyJarException(MYJ, "ESI-01", "Unauthorized");
    }
    public static MoneyJarException encryptionError() {
        return new MoneyJarException(MYJ, "ESI-02", "Something went wrong, please contact the developer");
    }
    public static MoneyJarException userCreationError() {
        return new MoneyJarException(MYJ, "USR-01", "user creation failed, email or username already used");
    }
    public static MoneyJarException userNotFoundError() {
        return new MoneyJarException(MYJ, "USR-02", "user not found");
    }


    public static MoneyJarException cardNotFoundError() {
        return new MoneyJarException(MYJ, "CRD-01", "card not found");
    }

    public static MoneyJarException transactionNotFoundError() {
        return new MoneyJarException(MYJ, "TRX-01", "Transaction not found");
    }
}
