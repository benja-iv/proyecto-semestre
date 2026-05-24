package com.smartfood.ms_despacho.service;

import com.smartfood.ms_despacho.client.NotificacionClient;
import com.smartfood.ms_despacho.client.PedidoClient;
import com.smartfood.ms_despacho.dto.DespachoRequestDTO;
import com.smartfood.ms_despacho.dto.DespachoResponseDTO;
import com.smartfood.ms_despacho.dto.NotificacionRequestExternalDTO;
import com.smartfood.ms_despacho.dto.PedidoResponseDTO;
import com.smartfood.ms_despacho.exception.DespachoNotFoundException;
import com.smartfood.ms_despacho.model.Despacho;
import com.smartfood.ms_despacho.repository.DespachoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DespachoService {
    private static final Logger logger = LoggerFactory.getLogger(DespachoService.class);
    private final DespachoRepository repository;
    private final PedidoClient pedidoClient;
    private final NotificacionClient notificacionClient;

    public DespachoService(DespachoRepository repository, PedidoClient pedidoClient, NotificacionClient notificacionClient) {
        this.repository = repository;
        this.pedidoClient = pedidoClient;
        this.notificacionClient = notificacionClient;
    }

    @Transactional
    public DespachoResponseDTO registrarDespacho(DespachoRequestDTO dto) {
        logger.info("Validando existencia del pedido ID {} mediante OpenFeign", dto.getPedidoId());
        Long clienteId;
        try {
            PedidoResponseDTO pedido = pedidoClient.obtenerPedidoPorId(dto.getPedidoId());
            if (pedido == null) {
                throw new RuntimeException("El pedido especificado no existe");
            }
            clienteId = pedido.getClienteId();
        } catch (Exception e) {
            logger.error("Error al consultar ms-pedidos: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar el pedido para crear el despacho");
        }

        logger.info("Registrando despacho para pedido ID: {}", dto.getPedidoId());
        Despacho despacho = new Despacho(null, dto.getPedidoId(), "PREPARANDO", dto.getDireccionEntrega(), LocalDateTime.now());
        Despacho guardado = repository.save(despacho);

        try {
            logger.info("Enviando notificacion al cliente ID {} mediante ms-notificaciones", clienteId);
            String mensaje = "Tu pedido #" + dto.getPedidoId() + " ha sido registrado para despacho y esta en estado PREPARANDO.";
            notificacionClient.enviar(new NotificacionRequestExternalDTO(clienteId, mensaje));
        } catch (Exception e) {
            logger.warn("No se pudo enviar la notificacion, pero el despacho fue registrado: {}", e.getMessage());
        }

        return mapearAResponse(guardado);
    }

    @Transactional(readOnly = true)
    public DespachoResponseDTO obtenerPorId(Long id) {
        Despacho despacho = repository.findById(id).orElseThrow(() -> new DespachoNotFoundException(id));
        return mapearAResponse(despacho);
    }

    private DespachoResponseDTO mapearAResponse(Despacho d) {
        return new DespachoResponseDTO(d.getId(), d.getPedidoId(), d.getEstado(), d.getDireccionEntrega(), d.getFechaActualizacion());
    }
}