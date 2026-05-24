package com.smartfood.ms_clientes.service;

import com.smartfood.ms_clientes.dto.ClienteRequestDTO;
import com.smartfood.ms_clientes.dto.ClienteResponseDTO;
import com.smartfood.ms_clientes.exception.ClienteNotFoundException;
import com.smartfood.ms_clientes.model.Cliente;
import com.smartfood.ms_clientes.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerTodos() {
        logger.info("Consultando lista completa de clientes");
        return clienteRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long id) {
        logger.info("Buscando cliente con ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cliente con ID {} no existe en la BD", id);
                    return new ClienteNotFoundException(id);
                });
                
        logger.debug("Cliente encontrado: nombre='{}', email='{}'", 
                cliente.getNombre(), cliente.getEmail());
                
        return mapearAResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO dto) {
        logger.info("Registrando nuevo cliente con email: '{}'", dto.getEmail());

        Optional<Cliente> existente = clienteRepository.findByEmail(dto.getEmail());
        if (existente.isPresent()) {
            logger.warn("Intento de registro con email ya existente: {}", dto.getEmail());
            throw new IllegalArgumentException("El email ingresado ya se encuentra registrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setFechaRegistro(LocalDateTime.now());

        Cliente guardado = clienteRepository.save(cliente);
        logger.info("Cliente registrado exitosamente. ID asignado: {}", guardado.getId());

        return mapearAResponseDTO(guardado);
    }

    @Transactional
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO dto) {
        logger.info("Actualizando cliente ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Intento de actualizar cliente inexistente. ID: {}", id);
                    return new ClienteNotFoundException(id);
                });

        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());

        Cliente actualizado = clienteRepository.save(cliente);
        logger.info("Cliente ID {} actualizado exitosamente", id);

        return mapearAResponseDTO(actualizado);
    }

    private ClienteResponseDTO mapearAResponseDTO(Cliente c) {
        return new ClienteResponseDTO(
                c.getId(),
                c.getNombre(),
                c.getEmail(),
                c.getTelefono(),
                c.getDireccion(),
                c.getFechaRegistro()
        );
    }
}