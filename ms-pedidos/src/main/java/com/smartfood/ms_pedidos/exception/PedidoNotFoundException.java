package com.smartfood.ms_pedidos.exception;

public class PedidoNotFoundException extends RuntimeException {

    private final Long pedidoId;

    public PedidoNotFoundException(Long id) {
        super("Pedido no encontrado con ID: " + id);
        this.pedidoId = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }
}