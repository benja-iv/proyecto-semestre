package com.smartfood.ms_clientes.repository;

import com.smartfood.ms_clientes.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de clientes con H2")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        cliente1 = entityManager.persistAndFlush(
            new Cliente(null, "Ana Morales", "ana@email.com", "+56912345678", "Av. Principal 123", LocalDateTime.now())
        );
        cliente2 = entityManager.persistAndFlush(
            new Cliente(null, "Roberto Gomez", "roberto@email.com", "+56987654321", "Calle Falsa 456", LocalDateTime.now())
        );
    }

    @Test
    @DisplayName("findAll() debe retornar todos los clientes insertados")
    void findAll_debeRetornarTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
    }

    @Test
    @DisplayName("findById() debe retornar cliente cuando existe")
    void findById_debeRetornarCliente_cuandoExiste() {
        Optional<Cliente> resultado = clienteRepository.findById(cliente1.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Ana Morales", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findById() debe retornar Optional vacio cuando no existe")
    void findById_debeRetornarVacio_cuandoNoExiste() {
        Optional<Cliente> resultado = clienteRepository.findById(99999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("findByEmail() debe retornar cliente con email existente")
    void findByEmail_debeRetornarCliente_cuandoEmailExiste() {
        Optional<Cliente> resultado = clienteRepository.findByEmail("ana@email.com");
        assertTrue(resultado.isPresent());
        assertEquals("Ana Morales", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findByEmail() debe retornar Optional vacio si email no existe")
    void findByEmail_debeRetornarVacio_cuandoEmailNoExiste() {
        Optional<Cliente> resultado = clienteRepository.findByEmail("noexiste@email.com");
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("save() debe persistir un nuevo cliente")
    void save_debePersistirCliente() {
        Cliente nuevo = new Cliente(null, "Maria Lopez", "maria@email.com", "+56911111111", "Calle Nueva 789", LocalDateTime.now());
        Cliente guardado = clienteRepository.save(nuevo);
        assertNotNull(guardado.getId());
        assertEquals("Maria Lopez", guardado.getNombre());
    }

    @Test
    @DisplayName("deleteById() debe eliminar el cliente")
    void deleteById_debeEliminarCliente() {
        clienteRepository.deleteById(cliente1.getId());
        Optional<Cliente> resultado = clienteRepository.findById(cliente1.getId());
        assertFalse(resultado.isPresent());
    }
}
