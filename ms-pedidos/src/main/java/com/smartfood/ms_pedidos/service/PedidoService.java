package com.smartfood.ms_pedidos.service;

import com.smartfood.ms_pedidos.dto.PedidoRequestDTO;
import com.smartfood.ms_pedidos.dto.PedidoResponseDTO;
import com.smartfood.ms_pedidos.exception.PedidoNotFoundException;
import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerTodos() {
        logger.info("Consultando lista completa de pedidos");
        return pedidoRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO obtenerPorId(Long id) {
        logger.info("Buscando pedido con ID: {}", id);
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pedido con ID {} no existe en la BD", id);
                    return new PedidoNotFoundException(id);
                });
                
        logger.debug("Pedido encontrado: cliente='{}', total={}, estado='{}'", 
                pedido.getCliente(), pedido.getTotal(), pedido.getEstado());
                
        return mapearAResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        logger.info("Creando pedido para cliente: '{}', total: {}", dto.getCliente(), dto.getTotal());

        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setDescripcion(dto.getDescripcion());
        pedido.setTotal(dto.getTotal());
        pedido.setEstado("PENDIENTE");
        pedido.setFechaCreacion(LocalDateTime.now());

        Pedido guardado = pedidoRepository.save(pedido);
        logger.info("Pedido creado exitosamente. ID asignado: {}", guardado.getId());

        return mapearAResponseDTO(guardado);
    }

    @Transactional
    public void actualizarEstado(Long id, String estado) {
        logger.info("Solicitud para actualizar estado de pedido ID: {} a '{}'", id, estado);

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Intento de actualizar estado en pedido inexistente. ID: {}", id);
                    return new PedidoNotFoundException(id);
                });

        String estadoAnterior = pedido.getEstado();
        pedido.setEstado(estado);
        pedidoRepository.save(pedido);

        logger.info("Estado actualizado. Pedido ID: {} | '{}' -> '{}'", id, estadoAnterior, estado);
    }

    private PedidoResponseDTO mapearAResponseDTO(Pedido p) {
        return new PedidoResponseDTO(
                p.getId(),
                p.getCliente(),
                p.getDescripcion(),
                p.getTotal(),
                p.getEstado(),
                p.getFechaCreacion()
        );
    }
}