package com.smartfood.ms_promociones.config;

import com.smartfood.ms_promociones.model.Promocion;
import com.smartfood.ms_promociones.repository.PromocionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final PromocionRepository repository;

    public DataInitializer(PromocionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Promocion(null, "PROMO10", 10, true));
            repository.save(new Promocion(null, "BIENVENIDO20", 20, true));
            repository.save(new Promocion(null, "EXPIRADO", 15, false));
        }
    }
}