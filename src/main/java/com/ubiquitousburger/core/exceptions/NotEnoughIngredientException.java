package com.ubiquitousburger.core.exceptions;

public class NotEnoughIngredientException extends RuntimeException {
    public NotEnoughIngredientException(String message) {
        super(message);
    }
}
