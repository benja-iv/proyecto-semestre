package com.smartfood.ms_notificaciones.controller;

import com.smartfood.ms_notificaciones.dto.NotificacionRequestDTO;
import com.smartfood.ms_notificaciones.dto.NotificacionResponseDTO;
import com.smartfood.ms_notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> enviar(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.ok(service.enviarNotificacion(dto));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.obtenerPorCliente(clienteId));
    }
}