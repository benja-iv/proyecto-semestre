package com.smartfood.ms_despacho.controller;

import com.smartfood.ms_despacho.dto.DespachoRequestDTO;
import com.smartfood.ms_despacho.dto.DespachoResponseDTO;
import com.smartfood.ms_despacho.service.DespachoService;
import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/despachos")
public class DespachoController {
    private final DespachoService service;

    private static final Logger logger = LoggerFactory.getLogger(DespachoController.class);

    public DespachoController(DespachoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DespachoResponseDTO> crearDespacho(@Valid @RequestBody DespachoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarDespacho(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DespachoResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/despachos recibido");
        return ResponseEntity.ok(service.obtenerTodos());
    }

}