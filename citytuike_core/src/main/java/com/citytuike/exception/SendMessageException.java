package com.citytuike.exception;

public class SendMessageException extends RuntimeException{
    public SendMessageException(String message){
        super (message);
    }
}
