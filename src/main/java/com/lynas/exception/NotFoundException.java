package com.lynas.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sazzad on 3/29/16
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public @Data class NotFoundException extends RuntimeException {
    private Object errorObject;
    private String errorMessage;
    private Object suggestionObject;
}
