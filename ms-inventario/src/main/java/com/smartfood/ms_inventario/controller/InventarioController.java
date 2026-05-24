package com.smartfood.ms_inventario.controller;

import com.smartfood.ms_inventario.dto.InventarioRequestDTO;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
@Validated
public class InventarioController {

    private static final Logger logger = LoggerFactory.getLogger(InventarioController.class);
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<InventarioResponseDTO> consultarPorProducto(@PathVariable Long productoId) {
        logger.debug("Peticion GET /api/inventario/producto/{}", productoId);
        return ResponseEntity.ok(inventarioService.consultarPorProductoId(productoId));
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> registrarOActualizar(@Valid @RequestBody InventarioRequestDTO dto) {
        logger.debug("Peticion POST /api/inventario para producto ID: {}", dto.getProductoId());
        return ResponseEntity.ok(inventarioService.registrarOActualizar(dto));
    }
}