package org.persona.moneyjar.utils;

import org.persona.moneyjar.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtInformationUtil {

    private JwtInformationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static User getUserDetails() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
