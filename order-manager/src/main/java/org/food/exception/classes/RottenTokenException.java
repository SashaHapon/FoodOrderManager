package org.food.exception.classes;


public class RottenTokenException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RottenTokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
