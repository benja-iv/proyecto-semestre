package com.smartfood.ms_catalogo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfood.ms_catalogo.client.InventarioClient;
import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final InventarioClient inventarioClient;

    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public Categoria guardarCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @GetMapping("/{id}/stock")
    public String obtenerCategoriaConStock(@PathVariable Long id) {
        String stockInfo = inventarioClient.obtenerStockPorId(id);
        return "Consulta desde Catálogo -> " + stockInfo;
    }
}