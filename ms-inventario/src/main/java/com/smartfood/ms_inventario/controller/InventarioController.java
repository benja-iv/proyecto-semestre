package com.smartfood.ms_inventario.controller;

import com.smartfood.ms_inventario.dto.InventarioRequestDTO;
import com.smartfood.ms_inventario.dto.InventarioResponseDTO;
import com.smartfood.ms_inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;

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

    @Operation(
        summary = "Lista todo el inventario",
        description = "Retorna el listado completo de existencias registradas para todos los productos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/inventario recibido");
        return ResponseEntity.ok(inventarioService.obtenerTodos());
    }

    @Operation(
        summary = "Consulta el stock de un producto",
        description = "Retorna la cantidad disponible en inventario para un producto específico, según su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock del producto obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe inventario registrado para ese producto")
    })
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<InventarioResponseDTO> consultarPorProducto(@PathVariable Long productoId) {
        logger.debug("Peticion GET /api/inventario/producto/{}", productoId);
        return ResponseEntity.ok(inventarioService.consultarPorProductoId(productoId));
    }

    @Operation(
        summary = "Registra o actualiza el stock de un producto",
        description = "Crea el registro de inventario para un producto nuevo, o actualiza la cantidad existente si ya estaba registrado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario registrado o actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> registrarOActualizar(@Valid @RequestBody InventarioRequestDTO dto) {
        logger.debug("Peticion POST /api/inventario para producto ID: {}", dto.getProductoId());
        return ResponseEntity.ok(inventarioService.registrarOActualizar(dto));
    }
}