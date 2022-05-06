/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jaxblib.exceptions;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= { Exception.class })
    public ResponseEntity<GenericResponse> handleServerException(Exception ex, WebRequest request) {
        log.severe("exception: "+ex.getClass().getName());
        log.severe("exception: "+ex.getMessage());
        log.severe("exception: "+ex.getCause());
        GenericResponse resp = new GenericResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return  new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
