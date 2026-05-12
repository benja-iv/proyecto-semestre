package com.smartfood.ms_pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_pedidos.dto.PedidoRequestDTO;
import com.smartfood.ms_pedidos.dto.PedidoResponseDTO;
import com.smartfood.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO dto) {
        return new ResponseEntity<>(pedidoService.procesarPedido(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtener(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.buscarPorId(id);
        if (response == null) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
      pedidoService.actualizarEstado(id, nuevoEstado);
      return ResponseEntity.ok().build();
}
}