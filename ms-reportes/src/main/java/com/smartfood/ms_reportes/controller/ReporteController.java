package com.smartfood.ms_reportes.controller;

import com.smartfood.ms_reportes.dto.ReporteRequestDTO;
import com.smartfood.ms_reportes.dto.ReporteResponseDTO;
import com.smartfood.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final ReporteService service;

    private static final Logger logger = LoggerFactory.getLogger(ReporteController.class);

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/reportes recibido");
        return ResponseEntity.ok(service.obtenerTodos());
    } 
    
    

    @PostMapping("/generar")
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO crear = service.generarReporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }
}