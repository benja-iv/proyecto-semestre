package com.smartfood.ms_catalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_catalogo.client.InventarioClient;
import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final InventarioClient inventarioClient;

    @GetMapping
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> guardarCategoria(@RequestBody CategoriaRequestDTO dto) {
        return new ResponseEntity<>(categoriaService.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Long id) {
        CategoriaResponseDTO response = categoriaService.buscarPorId(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<String> obtenerCategoriaConStock(@PathVariable Long id) {
        try {
            String stockInfo = inventarioClient.obtenerStockPorId(id);
            return ResponseEntity.ok("Consulta desde Catálogo -> " + stockInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No se encontró stock para el ID " + id);
        }
    }
}