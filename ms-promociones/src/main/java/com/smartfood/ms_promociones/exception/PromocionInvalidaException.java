package com.smartfood.ms_promociones.exception;

public class PromocionInvalidaException extends RuntimeException {
    public PromocionInvalidaException(String mensaje) {
        super(mensaje);
    }
}