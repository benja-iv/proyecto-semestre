package com.smartfood.ms_inventario.service;

import org.springframework.stereotype.Service;
import com.smartfood.ms_inventario.model.Inventario;
import com.smartfood.ms_inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public Optional<Inventario> buscarPorProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public List<Inventario> listarTodo() {
    return inventarioRepository.findAll();
}
}