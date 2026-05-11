package com.smartfood.ms_pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_pedidos.dto.PedidoDTO;
import com.smartfood.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody PedidoDTO dto) {
        return new ResponseEntity<>(pedidoService.procesarPedido(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtener(@PathVariable Long id) {
        PedidoDTO response = pedidoService.buscarPorId(id);
        if (response == null) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(response);
    }
}