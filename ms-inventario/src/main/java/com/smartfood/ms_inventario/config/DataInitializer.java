package com.smartfood.ms_inventario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartfood.ms_inventario.model.Inventario;
import com.smartfood.ms_inventario.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        inventarioRepository.saveAll(List.of(
            new Inventario(null, 1L, 50),
            new Inventario(null, 2L, 100),
            new Inventario(null, 3L, 20)
        ));
    }
}