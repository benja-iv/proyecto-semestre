package com.smartfood.ms_inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponseDTO> obtenerStock(@PathVariable Long id) {
        return inventarioRepository.findByProductoId(id)
                .map(inv -> ResponseEntity.ok(new InventarioResponseDTO(inv.getProductoId(), inv.getCantidad())))
                .orElse(ResponseEntity.notFound().build());
    }
}