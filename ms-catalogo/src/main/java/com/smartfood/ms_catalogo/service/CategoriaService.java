package com.smartfood.ms_catalogo.service;

import com.smartfood.ms_catalogo.client.InventarioClient;
import com.smartfood.ms_catalogo.dto.CategoriaRequestDTO;
import com.smartfood.ms_catalogo.dto.CategoriaResponseDTO;
import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final InventarioClient inventarioClient;

    public List<CategoriaResponseDTO> listarTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(this::convertirAResponseDto).collect(Collectors.toList());
    }

    public Optional<CategoriaResponseDTO> buscarPorId(Long id) {
        return categoriaRepository.findById(id).map(this::convertirAResponseDto);
    }

    public CategoriaResponseDTO guardar(CategoriaRequestDTO requestDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(requestDTO.getNombre());
        
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirAResponseDto(categoriaGuardada);
    }

    public String consultarInventarioExterno(Long id) {
        return inventarioClient.obtenerStockPorId(id);
    }

    private CategoriaResponseDTO convertirAResponseDto(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
}