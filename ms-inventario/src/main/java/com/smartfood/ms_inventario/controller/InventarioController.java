package com.smartfood.ms_inventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.ms_inventario.model.Inventario;
import com.smartfood.ms_inventario.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;

    @GetMapping("/{id}")
    public String obtenerId(@PathVariable Long id) {
        return inventarioRepository.findByProductoId(id)
                .map(inv -> "Hay " + inv.getCantidad() + " unidades del producto " + id)
                .orElse("Producto " + id + " no encontrado en inventario");
    }
}