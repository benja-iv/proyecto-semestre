package com.smartfood.ms_inventario.config;

import com.smartfood.ms_inventario.model.Inventario;
import com.smartfood.ms_inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final InventarioRepository inventarioRepository;

    public DataInitializer(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public void run(String... args) {
        if (inventarioRepository.count() == 0) {
            logger.info("Inicializando inventario...");
            inventarioRepository.save(new Inventario(null, 1L, 50, LocalDateTime.now()));
            inventarioRepository.save(new Inventario(null, 2L, 30, LocalDateTime.now()));
            logger.info("Inventario inicializado.");
        }
    }
}