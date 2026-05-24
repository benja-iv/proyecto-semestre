package com.smartfood.ms_reportes.client;

import com.smartfood.ms_reportes.dto.PedidoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-pedidos", url = "${ms.pedidos.url}")
public interface PedidoClient {
    @GetMapping
    List<PedidoResponseDTO> obtenerTodosLosPedidos();
}