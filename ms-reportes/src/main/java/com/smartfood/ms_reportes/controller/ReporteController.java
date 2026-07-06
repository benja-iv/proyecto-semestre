package com.smartfood.ms_reportes.controller;

import com.smartfood.ms_reportes.dto.ReporteRequestDTO;
import com.smartfood.ms_reportes.dto.ReporteResponseDTO;
import com.smartfood.ms_reportes.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
        summary = "Lista todos los reportes",
        description = "Retorna la lista completa de reportes generados previamente en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/reportes recibido");
        return ResponseEntity.ok(service.obtenerTodos());
    } 
    

    @Operation(
        summary = "Genera un nuevo reporte",
        description = "Crea un reporte nuevo a partir de los parámetros enviados (ej. rango de fechas, tipo de reporte)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos en la solicitud")
    })
    @PostMapping("/generar")
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO crear = service.generarReporte(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }
}
