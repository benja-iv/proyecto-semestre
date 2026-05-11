package com.smartfood.ms_clientes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_clientes.dto.ClienteDTO;
import com.smartfood.ms_clientes.model.Cliente;
import com.smartfood.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class CategoriaController {

    private final ClienteRepository clienteRepository;

    @GetMapping
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(c -> new ClienteDTO(c.getId(), c.getNombre(), c.getEmail(), c.getDireccion()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(c -> ResponseEntity.ok(new ClienteDTO(c.getId(), c.getNombre(), c.getEmail(), c.getDireccion())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> guardarCliente(@RequestBody ClienteDTO dto) {
        Cliente cliente = new Cliente(null, dto.getNombre(), dto.getEmail(), null, dto.getDireccion());
        Cliente guardado = clienteRepository.save(cliente);
        ClienteDTO respuesta = new ClienteDTO(guardado.getId(), guardado.getNombre(), guardado.getEmail(), guardado.getDireccion());
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}