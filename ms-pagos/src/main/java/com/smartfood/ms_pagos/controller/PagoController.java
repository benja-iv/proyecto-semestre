package com.smartfood.ms_pagos.controller;

import com.smartfood.ms_pagos.dto.PagoRequestDTO;
import com.smartfood.ms_pagos.dto.PagoResponseDTO;
import com.smartfood.ms_pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Validated
public class PagoController {

    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(
        summary = "Lista todos los pagos",
        description = "Retorna la lista completa de pagos procesados en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/pagos recibido");
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @Operation(
        summary = "Busca un pago por ID",
        description = "Retorna los datos de un pago específico según su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe un pago con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        logger.debug("GET /api/pagos/{} recibido", id);
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(
        summary = "Procesa un nuevo pago",
        description = "Registra el pago asociado a un pedido, comunicándose con ms-pedidos para validar y actualizar su estado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago procesado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o el pedido asociado no existe")
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> procesarPago(@Valid @RequestBody PagoRequestDTO dto) {
        logger.debug("POST /api/pagos recibido para pedido ID: {}", dto.getPedidoId());
        PagoResponseDTO creado = pagoService.procesarPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
