package com.smartfood.ms_pedidos.service;

import org.springframework.stereotype.Service;
import com.smartfood.ms_pedidos.client.InventarioClient;
import com.smartfood.ms_pedidos.dto.PedidoDTO;
import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final InventarioClient inventarioClient;

    public PedidoDTO procesarPedido(PedidoDTO dto) {
        inventarioClient.obtenerStockPorId(dto.getProductoId());
        
        Pedido pedido = new Pedido(null, dto.getClienteId(), dto.getProductoId(), dto.getCantidad(), dto.getTotal(), LocalDateTime.now());
        Pedido guardado = pedidoRepository.save(pedido);
        
        return new PedidoDTO(guardado.getId(), guardado.getClienteId(), guardado.getProductoId(), guardado.getCantidad(), guardado.getTotal(), guardado.getFechaPedido());
    }
}