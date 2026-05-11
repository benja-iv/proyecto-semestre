package com.smartfood.ms_catalogo.service;

import org.springframework.stereotype.Service;
import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(c -> new CategoriaResponseDTO(c.getId(), c.getNombre()))
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria(null, dto.getNombre());
        Categoria guardada = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(guardada.getId(), guardada.getNombre());
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(c -> new CategoriaResponseDTO(c.getId(), c.getNombre()))
                .orElse(null);
    }
}