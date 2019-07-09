package com.ubiquitousburger.core.burger.exceptions;

public class NotEnoughIngredient extends RuntimeException {
    public NotEnoughIngredient(String message) {
        super(message);
    }
}
