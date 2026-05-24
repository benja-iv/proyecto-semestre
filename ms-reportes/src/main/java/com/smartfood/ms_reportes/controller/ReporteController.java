package com.smartfood.ms_reportes.controller;

import com.smartfood.ms_reportes.dto.ReporteRequestDTO;
import com.smartfood.ms_reportes.dto.ReporteResponseDTO;
import com.smartfood.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @PostMapping("/generar")
    public ResponseEntity<ReporteResponseDTO> generar(@Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.ok(service.generarReporte(dto));
    }
}