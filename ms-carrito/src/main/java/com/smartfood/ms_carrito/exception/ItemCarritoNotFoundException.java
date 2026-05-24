package com.smartfood.ms_carrito.exception;

public class ItemCarritoNotFoundException extends RuntimeException {
    public ItemCarritoNotFoundException(Long id) {
        super("Item del carrito no encontrado con ID: " + id);
    }
}