package com.smartfood.ms_carrito.repository;

import com.smartfood.ms_carrito.model.ItemCarrito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de items del carrito con H2")
class ItemCarritoRepositoryTest {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager.persistAndFlush(new ItemCarrito(null, 1L, 1L, 2));
        entityManager.persistAndFlush(new ItemCarrito(null, 1L, 2L, 1));
        entityManager.persistAndFlush(new ItemCarrito(null, 2L, 1L, 3));
    }

    @Test
    @DisplayName("findByClienteId() debe retornar items del cliente")
    void findByClienteId_debeRetornarItems() {
        List<ItemCarrito> resultado = itemCarritoRepository.findByClienteId(1L);
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("findByClienteId() debe retornar lista vacia si cliente sin items")
    void findByClienteId_listaVacia() {
        List<ItemCarrito> resultado = itemCarritoRepository.findByClienteId(99L);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll() debe retornar todos los items")
    void findAll_debeRetornarTodos() {
        assertEquals(3, itemCarritoRepository.findAll().size());
    }

    @Test
    @DisplayName("save() debe persistir nuevo item")
    void save_debePersistir() {
        ItemCarrito nuevo = itemCarritoRepository.save(new ItemCarrito(null, 3L, 5L, 4));
        assertNotNull(nuevo.getId());
        assertEquals(3L, nuevo.getClienteId());
        assertEquals(4, nuevo.getCantidad());
    }

    @Test
    @DisplayName("existsById() debe retornar true cuando existe")
    void existsById_debeRetornarTrue() {
        List<ItemCarrito> items = itemCarritoRepository.findAll();
        assertTrue(itemCarritoRepository.existsById(items.get(0).getId()));
    }

    @Test
    @DisplayName("deleteById() debe eliminar el item")
    void deleteById_debeEliminar() {
        List<ItemCarrito> items = itemCarritoRepository.findAll();
        Long id = items.get(0).getId();
        itemCarritoRepository.deleteById(id);
        assertFalse(itemCarritoRepository.existsById(id));
    }
}
