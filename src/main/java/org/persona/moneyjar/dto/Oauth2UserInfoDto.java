package org.persona.moneyjar.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Satya
 * @created 04/07/2024 - 15:14
 **/

@Data
@Builder
public class Oauth2UserInfoDto {
    private String name;
    private String id;
    private String email;
    private String picture;
}
