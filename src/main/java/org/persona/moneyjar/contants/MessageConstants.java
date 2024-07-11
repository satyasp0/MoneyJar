package org.persona.moneyjar.contants;

/**
 * @author Satya
 * @created 09/07/2024 - 09:47
 **/
public class MessageConstants {
    private MessageConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final String USER_NOT_FOUND = "user not found";
    public static final String USER_UPDATED = "user updated successfully";
    public static final String USER_DELETED = "user deleted successfully";
    public static final String CREATE_FAILED = "user creation failed, email or username already used";
}
