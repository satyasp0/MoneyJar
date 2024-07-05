package org.persona.moneyjar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Satya
 * @created 12/8/2023 - 10:37 AM
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto {
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Object data;
}
