package com.smartfood.ms_notificaciones.controller;

import com.smartfood.ms_notificaciones.dto.NotificacionRequestDTO;
import com.smartfood.ms_notificaciones.dto.NotificacionResponseDTO;
import com.smartfood.ms_notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
        summary = "Envía una notificación",
        description = "Genera y envía una notificación a un cliente (ej. confirmación de pedido, estado de despacho)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación enviada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> enviar(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.ok(service.enviarNotificacion(dto));
    }

    @Operation(
        summary = "Lista todas las notificaciones",
        description = "Retorna el historial completo de notificaciones enviadas en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }


    @Operation(
        summary = "Lista las notificaciones de un cliente",
        description = "Retorna el historial de notificaciones enviadas a un cliente específico."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificaciones del cliente obtenidas correctamente")
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.obtenerPorCliente(clienteId));
    }
}
