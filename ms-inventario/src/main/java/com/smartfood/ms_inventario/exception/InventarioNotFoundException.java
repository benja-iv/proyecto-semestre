package com.smartfood.ms_inventario.exception;

public class InventarioNotFoundException extends RuntimeException {
    private final Long productoId;

    public InventarioNotFoundException(Long productoId) {
        super("Inventario no encontrado para producto ID: " + productoId);
        this.productoId = productoId;
    }

    public Long getProductoId() {
        return productoId;
    }
}