package com.smartfood.ms_despacho.exception;

public class DespachoNotFoundException extends RuntimeException {
    public DespachoNotFoundException(Long id) {
        super("Despacho no encontrado con ID: " + id);
    }
}