package org.persona.moneyjar.controller;

import org.persona.moneyjar.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Satya
 * @created 05/07/2024 - 13:18
 **/

@RestController
@RequestMapping("/")
public abstract class BaseController {

    protected ResponseEntity<BaseResponseDto> send200(Object data) {
        return ResponseEntity.ok(BaseResponseDto.builder()
                .status(200)
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build());
    }

    protected ResponseEntity<BaseResponseDto> send201(Object data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.builder()
                .status(201)
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(data)
                .build());
    }

    protected ResponseEntity<BaseResponseDto> send400(Object data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.builder()
                .status(400)
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(data)
                .build());
    }

    protected ResponseEntity<BaseResponseDto> send404(Object data) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseDto.builder()
                .status(404)
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .data(data)
                .build());
    }

    protected ResponseEntity<BaseResponseDto> send500() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponseDto.builder()
                .status(500)
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(null)
                .build());
    }

}
