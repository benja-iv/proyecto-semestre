package com.smartfood.ms_reportes.config;

import com.smartfood.ms_reportes.model.Reporte;
import com.smartfood.ms_reportes.repository.ReporteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ReporteRepository repository;

    public DataInitializer(ReporteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Reporte(null, "VENTAS_DIARIAS", LocalDateTime.now(), "COMPLETADO"));
            repository.save(new Reporte(null, "STOCK_CRITICO", LocalDateTime.now(), "COMPLETADO"));
        }
    }
}