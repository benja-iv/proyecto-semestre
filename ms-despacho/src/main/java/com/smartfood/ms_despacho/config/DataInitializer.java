package com.smartfood.ms_despacho.config;

import com.smartfood.ms_despacho.model.Despacho;
import com.smartfood.ms_despacho.repository.DespachoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final DespachoRepository repository;

    public DataInitializer(DespachoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Despacho(null, 101L, "EN_CAMINO", "Av. Principal 123", LocalDateTime.now()));
            repository.save(new Despacho(null, 102L, "PREPARANDO", "Calle Los Olivos 456", LocalDateTime.now()));
        }
    }
}