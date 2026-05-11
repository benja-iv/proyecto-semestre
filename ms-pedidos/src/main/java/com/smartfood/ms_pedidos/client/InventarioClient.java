package com.smartfood.ms_pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-inventario", url = "http://localhost:8081")
public interface InventarioClient {
    @GetMapping("/api/inventario/{id}")
    String obtenerStockPorId(@PathVariable("id") Long id);
}