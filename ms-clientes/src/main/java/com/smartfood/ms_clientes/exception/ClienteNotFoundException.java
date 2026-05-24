package com.smartfood.ms_clientes.exception;

public class ClienteNotFoundException extends RuntimeException {

    private final Long clienteId;

    public ClienteNotFoundException(Long id) {
        super("Cliente no encontrado con ID: " + id);
        this.clienteId = id;
    }

    public Long getClienteId() {
        return clienteId;
    }
}