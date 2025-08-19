package com.roqia.to_do_list_demo.exceptionHandlers;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException() {
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
