package com.smartfood.ms_pagos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-pedidos", url = "${ms-pedidos.url:http://localhost:8080}")
public interface PedidoClient {

    @PutMapping("/api/pedidos/{id}/estado")
    void actualizarEstado(@PathVariable("id") Long id, @RequestParam("estado") String estado);
}