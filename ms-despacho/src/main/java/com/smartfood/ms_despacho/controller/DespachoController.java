package com.smartfood.ms_despacho.controller;

import com.smartfood.ms_despacho.dto.DespachoRequestDTO;
import com.smartfood.ms_despacho.dto.DespachoResponseDTO;
import com.smartfood.ms_despacho.service.DespachoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
        summary = "Registra un nuevo despacho",
        description = "Crea el registro de despacho para un pedido ya pagado, iniciando el proceso de entrega."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Despacho registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<DespachoResponseDTO> crearDespacho(@Valid @RequestBody DespachoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarDespacho(dto));
    }

    @Operation(
        summary = "Busca un despacho por ID",
        description = "Retorna los datos de un despacho específico según su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despacho encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe un despacho con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DespachoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(
        summary = "Lista todos los despachos",
        description = "Retorna la lista completa de despachos registrados en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de despachos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<DespachoResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/despachos recibido");
        return ResponseEntity.ok(service.obtenerTodos());
    }

}
