package com.smartfood.ms_catalogo.exception;

public class CategoriaNotFoundException extends RuntimeException {
    private final Long categoriaId;

    public CategoriaNotFoundException(Long id) {
        super("Categoria no encontrada con ID: " + id);
        this.categoriaId = id;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }
}