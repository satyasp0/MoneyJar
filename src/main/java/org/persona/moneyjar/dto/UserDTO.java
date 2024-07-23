package org.persona.moneyjar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Satya
 * @created 05/07/2024 - 13:54
 **/


@Data
public class UserDTO {

    private String password;
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name cannot be null")
    private String name;
    private String picture;
    @NotBlank(message = "User name is mandatory")
    private String username;
    @NotBlank(message = "E-mail is mandatory")
    private String email;
}

