package com.smartfood.ms_catalogo.controller;

import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@Validated
public class CategoriaController {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(
        summary = "Lista todas las categorías",
        description = "Retorna la lista completa de categorías de productos registradas en el catálogo."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerTodas() {
        logger.debug("Peticion GET /api/categorias");
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    @Operation(
        summary = "Busca una categoría por ID",
        description = "Retorna los datos de una categoría específica según su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe una categoría con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorId(@PathVariable Long id) {
        logger.debug("Peticion GET /api/categorias/{}", id);
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    @Operation(
        summary = "Crea una nueva categoría",
        description = "Registra una nueva categoría de productos en el catálogo."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {
        logger.debug("Peticion POST /api/categorias");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(dto));
    }

    @Operation(
        summary = "Actualiza una categoría existente",
        description = "Modifica los datos de una categoría ya registrada, identificada por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "404", description = "No existe una categoría con ese ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Long id, 
            @Valid @RequestBody CategoriaRequestDTO dto) {
        logger.debug("Peticion PUT /api/categorias/{}", id);
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, dto));
    }
}
