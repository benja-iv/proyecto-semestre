package com.smartfood.ms_pedidos.service;

import org.springframework.stereotype.Service;
import com.smartfood.ms_pedidos.client.InventarioClient;
import com.smartfood.ms_pedidos.dto.PedidoRequestDTO;
import com.smartfood.ms_pedidos.dto.PedidoResponseDTO;
import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final InventarioClient inventarioClient;

    public PedidoResponseDTO procesarPedido(PedidoRequestDTO dto) {
        inventarioClient.obtenerStockPorId(dto.getProductoId());
        
        Pedido pedido = new Pedido(null, dto.getClienteId(), dto.getProductoId(), dto.getCantidad(), 0.0, LocalDateTime.now(), "PENDIENTE");
        Pedido guardado = pedidoRepository.save(pedido);
        
        return new PedidoResponseDTO(guardado.getId(), guardado.getClienteId(), guardado.getProductoId(), guardado.getCantidad(), guardado.getTotal(), guardado.getFechaPedido());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(p -> new PedidoResponseDTO(p.getId(), p.getClienteId(), p.getProductoId(), p.getCantidad(), p.getTotal(), p.getFechaPedido()))
                .orElse(null);
    }

    public void actualizarEstado(Long id, String nuevoEstado) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    
    pedido.setEstado(nuevoEstado);
    pedidoRepository.save(pedido);
}
}