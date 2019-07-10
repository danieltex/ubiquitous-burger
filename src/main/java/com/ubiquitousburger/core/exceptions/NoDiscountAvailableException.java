package com.ubiquitousburger.core.exceptions;

public class NoDiscountAvailableException extends RuntimeException {
    public NoDiscountAvailableException(String message) {
        super(message);
    }
}
