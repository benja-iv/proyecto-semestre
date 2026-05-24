package com.smartfood.ms_catalogo.service;

import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.exception.CategoriaNotFoundException;
import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerTodas() {
        logger.info("Consultando todas las categorias");
        return categoriaRepository.findAll().stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Long id) {
        logger.info("Buscando categoria con ID: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Categoria ID {} no encontrada", id);
                    return new CategoriaNotFoundException(id);
                });
        return mapearAResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {
        logger.info("Creando categoria: '{}'", dto.getNombre());
        
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        
        Categoria guardada = categoriaRepository.save(categoria);
        logger.info("Categoria creada con ID: {}", guardada.getId());
        
        return mapearAResponseDTO(guardada);
    }

    @Transactional
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto) {
        logger.info("Actualizando categoria ID: {}", id);
        
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Intento de actualizar categoria inexistente. ID: {}", id);
                    return new CategoriaNotFoundException(id);
                });

        categoria.setNombre(dto.getNombre());
        
        Categoria actualizada = categoriaRepository.save(categoria);
        logger.info("Categoria ID {} actualizada exitosamente", id);
        
        return mapearAResponseDTO(actualizada);
    }

    private CategoriaResponseDTO mapearAResponseDTO(Categoria c) {
        return new CategoriaResponseDTO(c.getId(), c.getNombre());
    }
}