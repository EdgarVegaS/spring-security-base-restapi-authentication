package com.tibianos.tibianosfanpage.exceptions;

public class EmailExistExeption extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public EmailExistExeption(String message){
        super(message);
    }
}
