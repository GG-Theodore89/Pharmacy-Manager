package com.pharmacie.exception;

public class PharmacieException extends RuntimeException {

    public PharmacieException(String message) {
        super(message);
    }

    public PharmacieException(String message, Throwable cause) {
        super(message, cause);
    }
}