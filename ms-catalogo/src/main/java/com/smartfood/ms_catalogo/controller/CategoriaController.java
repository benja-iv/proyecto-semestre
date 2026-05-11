package com.smartfood.ms_catalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> guardarCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {
        return new ResponseEntity<>(categoriaService.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Long id) {
        CategoriaResponseDTO response = categoriaService.buscarPorId(id);
        if (response == null) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(response);
    }
}