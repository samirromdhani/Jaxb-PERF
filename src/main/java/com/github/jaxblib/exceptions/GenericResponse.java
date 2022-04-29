/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jaxblib.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse {

    private int status = HttpStatus.OK.value();
    private String description = HttpStatus.OK.name();
    private String message = "";
    private String path = "";
    private Date timestamp = new Date();

    public GenericResponse(String msg, int st){
        message = msg;
        status = st;
        description = HttpStatus.valueOf(st).name();
    }

    public GenericResponse(String msg, int st, String path){
        this(msg,st);
        this.path = path;
    }
}
