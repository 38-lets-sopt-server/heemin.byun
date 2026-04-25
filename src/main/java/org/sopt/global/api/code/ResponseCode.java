package org.sopt.global.api.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    HttpStatus getStatus();
    String getMessage();
}
