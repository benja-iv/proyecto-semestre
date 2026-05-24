package com.smartfood.ms_despacho.client;

import com.smartfood.ms_despacho.dto.PedidoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pedidos", url = "${ms.pedidos.url}")
public interface PedidoClient {
    @GetMapping("/{id}")
    PedidoResponseDTO obtenerPedidoPorId(@PathVariable("id") Long id);
}