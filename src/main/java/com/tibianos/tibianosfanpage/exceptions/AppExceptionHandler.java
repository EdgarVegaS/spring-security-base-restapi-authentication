package com.tibianos.tibianosfanpage.exceptions;

import java.util.Date;

import com.tibianos.tibianosfanpage.models.responses.ErrorMessage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {
    
    @ExceptionHandler(value = EmailExistExeption.class )
    public ResponseEntity<Object> handleEmailExistException(EmailExistExeption ex, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler(value = Exception.class )
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );
    }
}
