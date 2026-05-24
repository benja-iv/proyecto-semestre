package com.smartfood.ms_pagos.service;

import com.smartfood.ms_pagos.client.PedidoClient;
import com.smartfood.ms_pagos.dto.PagoRequestDTO;
import com.smartfood.ms_pagos.dto.PagoResponseDTO;
import com.smartfood.ms_pagos.exception.PagoNotFoundException;
import com.smartfood.ms_pagos.model.Pago;
import com.smartfood.ms_pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final PedidoClient pedidoClient;

    public PagoService(PagoRepository pagoRepository, PedidoClient pedidoClient) {
        this.pagoRepository = pagoRepository;
        this.pedidoClient = pedidoClient;
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerTodos() {
        logger.info("Consultando lista completa de pagos");
        return pagoRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPorId(Long id) {
        logger.info("Buscando pago con ID: {}", id);
        
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Pago con ID {} no existe en la BD", id);
                    return new PagoNotFoundException(id);
                });
                
        logger.debug("Pago encontrado: pedidoId={}, monto={}, estado='{}'", 
                pago.getPedidoId(), pago.getMonto(), pago.getEstado());
                
        return mapearAResponseDTO(pago);
    }

    @Transactional
    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {
        logger.info("Procesando pago para pedido ID: {}, monto: {}", dto.getPedidoId(), dto.getMonto());

        pedidoClient.actualizarEstado(dto.getPedidoId(), "PAGADO");

        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado("APROBADO");
        pago.setFechaPago(LocalDateTime.now());

        Pago guardado = pagoRepository.save(pago);
        logger.info("Pago procesado exitosamente. ID asignado: {}", guardado.getId());

        return mapearAResponseDTO(guardado);
    }

    private PagoResponseDTO mapearAResponseDTO(Pago p) {
        return new PagoResponseDTO(
                p.getId(),
                p.getPedidoId(),
                p.getMonto(),
                p.getMetodoPago(),
                p.getEstado(),
                p.getFechaPago()
        );
    }
}