package com.smartfood.ms_catalogo.repository;

import com.smartfood.ms_catalogo.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests del repositorio de categorias con H2")
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Categoria cat1;
    private Categoria cat2;

    @BeforeEach
    void setUp() {
        cat1 = entityManager.persistAndFlush(new Categoria(null, "Pizzas"));
        cat2 = entityManager.persistAndFlush(new Categoria(null, "Bebidas"));
    }

    @Test
    @DisplayName("findAll() debe retornar todas las categorias")
    void findAll_debeRetornarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        assertEquals(2, categorias.size());
    }

    @Test
    @DisplayName("findById() debe retornar la categoria cuando existe")
    void findById_debeRetornarCategoria() {
        Optional<Categoria> resultado = categoriaRepository.findById(cat1.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Pizzas", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findById() debe retornar vacio cuando no existe")
    void findById_debeRetornarVacio() {
        Optional<Categoria> resultado = categoriaRepository.findById(99999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("save() debe persistir una nueva categoria")
    void save_debePersistirCategoria() {
        Categoria nueva = categoriaRepository.save(new Categoria(null, "Postres"));
        assertNotNull(nueva.getId());
        assertEquals("Postres", nueva.getNombre());
    }

    @Test
    @DisplayName("deleteById() debe eliminar la categoria")
    void deleteById_debeEliminar() {
        categoriaRepository.deleteById(cat1.getId());
        assertFalse(categoriaRepository.findById(cat1.getId()).isPresent());
    }
}
