package com.smartfood.ms_promociones.controller;

import com.smartfood.ms_promociones.dto.PromocionRequestDTO;
import com.smartfood.ms_promociones.dto.PromocionResponseDTO;
import com.smartfood.ms_promociones.service.PromocionService;
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
@RequestMapping("/api/promociones")
public class PromocionController {
    private final PromocionService service;

    private static final Logger logger = LoggerFactory.getLogger(PromocionController.class);

    public PromocionController(PromocionService service) {
        this.service = service;
    }

    @Operation(
        summary = "Lista todas las promociones",
        description = "Retorna la lista completa de promociones registradas en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de promociones obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<PromocionResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/promociones recibido");
        return ResponseEntity.ok(service.obtenerTodos());
    }    

    @Operation(
        summary = "Valida un código de promoción",
        description = "Verifica si un código de promoción es válido y retorna sus datos (porcentaje de descuento, vigencia, etc.)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Código válido, promoción retornada"),
        @ApiResponse(responseCode = "404", description = "El código no existe o no está vigente")
    })
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<PromocionResponseDTO> validarCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(service.validarCodigo(codigo));
    }

    @Operation(
        summary = "Crea una nueva promoción",
        description = "Registra una nueva promoción con su código, descuento y condiciones de uso."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Promoción creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crear(@Valid @RequestBody PromocionRequestDTO dto) {
        PromocionResponseDTO crear = service.crearPromocion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }
}
