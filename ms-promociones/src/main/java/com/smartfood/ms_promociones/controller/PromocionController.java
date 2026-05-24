package com.smartfood.ms_promociones.controller;

import com.smartfood.ms_promociones.dto.PromocionRequestDTO;
import com.smartfood.ms_promociones.dto.PromocionResponseDTO;
import com.smartfood.ms_promociones.service.PromocionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {
    private final PromocionService service;

    public PromocionController(PromocionService service) {
        this.service = service;
    }

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<PromocionResponseDTO> validarCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(service.validarCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crear(@Valid @RequestBody PromocionRequestDTO dto) {
        return ResponseEntity.ok(service.crearPromocion(dto));
    }
}