package com.lynas.model.json.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LynAs on 29-Mar-16
 */
@NoArgsConstructor
@AllArgsConstructor
public @Data class ErrorResponse {
    private String originPoint;
    private String message;
    private Object receivedObject;

}
