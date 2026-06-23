package com.smartfood.ms_pedidos.repository;

import com.smartfood.ms_pedidos.model.Pedido;
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
@DisplayName("Tests del repositorio de pedidos con H2")
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Pedido pedido1;
    private Pedido pedido2;

    @BeforeEach
    void setUp() {
        pedido1 = entityManager.persistAndFlush(new Pedido(null, "Juan Perez", "2 Hamburguesas", 15000.0, "PENDIENTE", LocalDateTime.now()));
        pedido2 = entityManager.persistAndFlush(new Pedido(null, "Maria Gomez", "1 Pizza", 25000.0, "PAGADO", LocalDateTime.now()));
    }

    @Test
    @DisplayName("findAll() debe retornar todos los pedidos")
    void findAll_debeRetornarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        assertEquals(2, pedidos.size());
    }

    @Test
    @DisplayName("findById() debe retornar pedido cuando existe")
    void findById_debeRetornarPedido() {
        Optional<Pedido> resultado = pedidoRepository.findById(pedido1.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Juan Perez", resultado.get().getCliente());
    }

    @Test
    @DisplayName("findById() debe retornar vacio cuando no existe")
    void findById_debeRetornarVacio() {
        assertFalse(pedidoRepository.findById(99999L).isPresent());
    }

    @Test
    @DisplayName("save() debe persistir nuevo pedido")
    void save_debePersistirPedido() {
        Pedido nuevo = pedidoRepository.save(new Pedido(null, "Carlos", "Sushi", 35000.0, "PENDIENTE", LocalDateTime.now()));
        assertNotNull(nuevo.getId());
        assertEquals("Carlos", nuevo.getCliente());
    }

    @Test
    @DisplayName("count() debe retornar cantidad correcta")
    void count_debeRetornarCantidadCorrecta() {
        assertEquals(2, pedidoRepository.count());
    }
}
