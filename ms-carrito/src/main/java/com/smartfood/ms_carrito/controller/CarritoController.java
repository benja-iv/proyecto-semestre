package com.smartfood.ms_carrito.controller;

import com.smartfood.ms_carrito.dto.ItemCarritoRequestDTO;
import com.smartfood.ms_carrito.model.ItemCarrito;
import com.smartfood.ms_carrito.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ItemCarrito>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.obtenerPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<ItemCarrito> agregarItem(@Valid @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.ok(service.agregarItem(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        service.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}