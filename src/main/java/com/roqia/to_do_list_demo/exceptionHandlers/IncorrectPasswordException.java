package com.roqia.to_do_list_demo.exceptionHandlers;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
