package org.demo.pc1_demo.exceptions;

public class UnauthorizeOperationException extends RuntimeException{
    public UnauthorizeOperationException(String message) {
        super(message);
    }
}