package com.smartfood.ms_catalogo.service;

import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.exception.CategoriaNotFoundException;
import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios de CategoriaService")
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoriaEjemplo;

    @BeforeEach
    void setUp() {
        categoriaEjemplo = new Categoria(1L, "Pizzas");
    }

    @Test
    @DisplayName("obtenerTodas() debe retornar lista de categorias")
    void obtenerTodas_debeRetornarLista() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoriaEjemplo));
        List<CategoriaResponseDTO> resultado = categoriaService.obtenerTodas();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pizzas", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("obtenerTodas() debe retornar lista vacia")
    void obtenerTodas_listaVacia() {
        when(categoriaRepository.findAll()).thenReturn(List.of());
        assertTrue(categoriaService.obtenerTodas().isEmpty());
    }

    @Test
    @DisplayName("obtenerPorId() debe retornar categoria cuando existe")
    void obtenerPorId_debeRetornarCategoria() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaEjemplo));
        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Pizzas", resultado.getNombre());
    }

    @Test
    @DisplayName("obtenerPorId() debe lanzar excepcion cuando no existe")
    void obtenerPorId_debeLanzarExcepcion() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("crearCategoria() debe guardar y retornar la categoria")
    void crearCategoria_debeGuardarYRetornar() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaEjemplo);
        CategoriaResponseDTO resultado = categoriaService.crearCategoria(new CategoriaRequestDTO("Pizzas"));
        assertNotNull(resultado);
        assertEquals("Pizzas", resultado.getNombre());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    @DisplayName("actualizarCategoria() debe actualizar y retornar")
    void actualizarCategoria_debeActualizarYRetornar() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaEjemplo));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(new Categoria(1L, "Comida Rapida"));
        CategoriaResponseDTO resultado = categoriaService.actualizarCategoria(1L, new CategoriaRequestDTO("Comida Rapida"));
        assertNotNull(resultado);
        assertEquals("Comida Rapida", resultado.getNombre());
    }

    @Test
    @DisplayName("actualizarCategoria() debe lanzar excepcion si no existe")
    void actualizarCategoria_debeLanzarExcepcion() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.actualizarCategoria(99L, new CategoriaRequestDTO("Test")));
    }
}
