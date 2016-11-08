package com.lynas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lynas on 3/30/16
 */
public class AppExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundError(NotFoundException ee) {
        return new ErrorResponse(ee.getErrorObject(), ee.getErrorMessage(), ee.getSuggestionObject());
    }


    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidError(InvalidException ie) {
        return new ErrorResponse(ie.getErrorObject(), ie.getErrorMessage(), ie.getSuggestionObject());
    }
}
