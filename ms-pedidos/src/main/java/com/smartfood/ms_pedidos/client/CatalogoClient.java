package com.smartfood.ms_pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.smartfood.ms_pedidos.dto.CategoriaResponseDTO;

@FeignClient(name = "ms-catalogo", url = "http://localhost:8080")
public interface CatalogoClient {
    @GetMapping("/api/categorias/{id}")
    CategoriaResponseDTO obtenerCategoriaPorId(@PathVariable("id") Long id);
}