package com.smartfood.ms_clientes.controller;

import com.smartfood.ms_clientes.dto.ClienteRequestDTO;
import com.smartfood.ms_clientes.dto.ClienteResponseDTO;
import com.smartfood.ms_clientes.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
    summary = "Lista todos los clientes",
    description = "Retorna la lista completa de clientes registrados en el sistema."
     )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/clientes recibido");
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }


    @Operation(
    summary = "Lista datos del cliente con la id solicitada",
    description = "Busca y retorna los datos del cliente de la ID solicitada"
     )
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        logger.debug("GET /api/clientes/{} recibido", id);
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }


    @Operation(
    summary = "Registra un nuevo cliente",
    description = "Ingresa un nuevo cliente al registro del sistema"
     )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        logger.debug("POST /api/clientes recibido para email: '{}'", dto.getEmail());
        ClienteResponseDTO creado = clienteService.registrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @Operation(
    summary = "Modifica al cliente indicado mediante su ID",
    description = "Actualiza el registro de un cliente utilizando su ID."
     )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long id, 
            @Valid @RequestBody ClienteRequestDTO dto) {
        logger.debug("PUT /api/clientes/{} recibido", id);
        ClienteResponseDTO actualizado = clienteService.actualizarCliente(id, dto);
        return ResponseEntity.ok(actualizado);
    }
}