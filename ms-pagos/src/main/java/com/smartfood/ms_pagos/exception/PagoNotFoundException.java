package com.smartfood.ms_pagos.exception;

public class PagoNotFoundException extends RuntimeException {

    private final Long pagoId;

    public PagoNotFoundException(Long id) {
        super("Pago no encontrado con ID: " + id);
        this.pagoId = id;
    }

    public Long getPagoId() {
        return pagoId;
    }
}