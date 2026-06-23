package com.smartfood.ms_clientes.service;

import com.smartfood.ms_clientes.dto.ClienteRequestDTO;
import com.smartfood.ms_clientes.dto.ClienteResponseDTO;
import com.smartfood.ms_clientes.exception.ClienteNotFoundException;
import com.smartfood.ms_clientes.model.Cliente;
import com.smartfood.ms_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteEjemplo;
    private ClienteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        clienteEjemplo = new Cliente(1L, "Ana Morales", "ana@email.com", "+56912345678", "Av. Principal 123", LocalDateTime.now());
        requestDTO = new ClienteRequestDTO("Ana Morales", "ana@email.com", "+56912345678", "Av. Principal 123");
    }

    @Test
    @DisplayName("obtenerTodos() debe retornar lista de clientes")
    void obtenerTodos_debeRetornarLista() {
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEjemplo));
        List<ClienteResponseDTO> resultado = clienteService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ana Morales", resultado.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerTodos() debe retornar lista vacia cuando no hay clientes")
    void obtenerTodos_debeRetornarListaVacia() {
        when(clienteRepository.findAll()).thenReturn(List.of());
        List<ClienteResponseDTO> resultado = clienteService.obtenerTodos();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("obtenerPorId() debe retornar cliente cuando existe")
    void obtenerPorId_debeRetornarCliente_cuandoExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEjemplo));
        ClienteResponseDTO resultado = clienteService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Ana Morales", resultado.getNombre());
        assertEquals("ana@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("obtenerPorId() debe lanzar excepcion cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class, () -> clienteService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("registrarCliente() debe guardar y retornar el cliente creado")
    void registrarCliente_debeGuardarYRetornar() {
        when(clienteRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEjemplo);
        ClienteResponseDTO resultado = clienteService.registrarCliente(requestDTO);
        assertNotNull(resultado);
        assertEquals("Ana Morales", resultado.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("registrarCliente() debe lanzar excepcion si el email ya existe")
    void registrarCliente_debeLanzarExcepcion_siEmailDuplicado() {
        when(clienteRepository.findByEmail("ana@email.com")).thenReturn(Optional.of(clienteEjemplo));
        assertThrows(IllegalArgumentException.class, () -> clienteService.registrarCliente(requestDTO));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("actualizarCliente() debe actualizar y retornar el cliente")
    void actualizarCliente_debeActualizarYRetornar() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEjemplo));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEjemplo);
        ClienteResponseDTO resultado = clienteService.actualizarCliente(1L, requestDTO);
        assertNotNull(resultado);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("actualizarCliente() debe lanzar excepcion si el cliente no existe")
    void actualizarCliente_debeLanzarExcepcion_cuandoNoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class, () -> clienteService.actualizarCliente(99L, requestDTO));
    }
}
