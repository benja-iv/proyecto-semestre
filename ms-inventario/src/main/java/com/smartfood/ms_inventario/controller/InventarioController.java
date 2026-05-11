package com.smartfood.ms_inventario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.service.InventarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> listarTodo() {
        List<InventarioResponseDTO> lista = inventarioService.listarTodo().stream()
            .map(inv -> new InventarioResponseDTO(inv.getProductoId(), inv.getCantidad()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtenerStock(@PathVariable Long id) {
        return inventarioService.buscarPorProductoId(id)
                .map(inv -> ResponseEntity.ok(new InventarioResponseDTO(inv.getProductoId(), inv.getCantidad())))
                .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado en inventario"));
    }
}