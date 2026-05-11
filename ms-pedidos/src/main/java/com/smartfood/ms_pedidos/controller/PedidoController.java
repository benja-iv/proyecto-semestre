package com.smartfood.ms_pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_pedidos.dto.PedidoDTO;
import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    @GetMapping
    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAll().stream()
                .map(p -> new PedidoDTO(p.getId(), p.getClienteId(), p.getProductoId(), p.getCantidad(), p.getTotal(), p.getFechaPedido()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO dto) {
        Pedido pedido = new Pedido(null, dto.getClienteId(), dto.getProductoId(), dto.getCantidad(), dto.getTotal(), LocalDateTime.now());
        Pedido guardado = pedidoRepository.save(pedido);
        return new ResponseEntity<>(new PedidoDTO(guardado.getId(), guardado.getClienteId(), guardado.getProductoId(), guardado.getCantidad(), guardado.getTotal(), guardado.getFechaPedido()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(p -> ResponseEntity.ok(new PedidoDTO(p.getId(), p.getClienteId(), p.getProductoId(), p.getCantidad(), p.getTotal(), p.getFechaPedido())))
                .orElse(ResponseEntity.notFound().build());
    }
}