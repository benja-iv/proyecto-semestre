package com.smartfood.ms_promociones.controller;

import com.smartfood.ms_promociones.dto.PromocionRequestDTO;
import com.smartfood.ms_promociones.dto.PromocionResponseDTO;
import com.smartfood.ms_promociones.service.PromocionService;
import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {
    private final PromocionService service;

    private static final Logger logger = LoggerFactory.getLogger(PromocionController.class);

    public PromocionController(PromocionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PromocionResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/promociones recibido");
        return ResponseEntity.ok(service.obtenerTodos());
    }    

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<PromocionResponseDTO> validarCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(service.validarCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crear(@Valid @RequestBody PromocionRequestDTO dto) {
        PromocionResponseDTO crear = service.crearPromocion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }
}