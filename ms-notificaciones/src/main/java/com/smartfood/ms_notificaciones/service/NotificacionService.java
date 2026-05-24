package com.smartfood.ms_notificaciones.service;

import com.smartfood.ms_notificaciones.dto.NotificacionRequestDTO;
import com.smartfood.ms_notificaciones.dto.NotificacionResponseDTO;
import com.smartfood.ms_notificaciones.model.Notificacion;
import com.smartfood.ms_notificaciones.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {
    private static final Logger logger = LoggerFactory.getLogger(NotificacionService.class);
    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public NotificacionResponseDTO enviarNotificacion(NotificacionRequestDTO dto) {
        logger.info("Enviando notificacion a Cliente ID: {}", dto.getClienteId());
        Notificacion notificacion = new Notificacion(null, dto.getClienteId(), dto.getMensaje(), LocalDateTime.now());
        Notificacion guardada = repository.save(notificacion);
        return new NotificacionResponseDTO(guardada.getId(), guardada.getClienteId(), guardada.getMensaje(), guardada.getFechaEnvio());
    }

    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> obtenerPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .map(n -> new NotificacionResponseDTO(n.getId(), n.getClienteId(), n.getMensaje(), n.getFechaEnvio()))
                .collect(Collectors.toList());
    }
}