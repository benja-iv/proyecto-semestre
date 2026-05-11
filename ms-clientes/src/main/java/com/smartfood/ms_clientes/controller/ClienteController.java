package com.smartfood.ms_clientes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_clientes.dto.ClienteRequestDTO;
import com.smartfood.ms_clientes.dto.ClienteResponseDTO;
import com.smartfood.ms_clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardarCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        return new ResponseEntity<>(clienteService.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.buscarPorId(id);
        if (response == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(response);
    }
}