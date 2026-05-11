package com.smartfood.ms_clientes.service;

import org.springframework.stereotype.Service;
import com.smartfood.ms_clientes.dto.ClienteRequestDTO;
import com.smartfood.ms_clientes.dto.ClienteResponseDTO;
import com.smartfood.ms_clientes.model.Cliente;
import com.smartfood.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(c -> new ClienteResponseDTO(c.getId(), c.getNombre(), c.getEmail(), c.getDireccion()))
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(c -> new ClienteResponseDTO(c.getId(), c.getNombre(), c.getEmail(), c.getDireccion()))
                .orElse(null);
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente(null, dto.getNombre(), dto.getEmail(), null, dto.getDireccion());
        Cliente guardado = clienteRepository.save(cliente);
        return new ClienteResponseDTO(guardado.getId(), guardado.getNombre(), guardado.getEmail(), guardado.getDireccion());
    }
}