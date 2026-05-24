package com.smartfood.ms_pagos.config;

import com.smartfood.ms_pagos.model.Pago;
import com.smartfood.ms_pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final PagoRepository pagoRepository;

    public DataInitializer(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public void run(String... args) {
        if (pagoRepository.count() > 0) {
            logger.info("DataInitializer: BD ya tiene {} pago(s). Omitiendo carga inicial.",
                    pagoRepository.count());
            return;
        }

        logger.info("DataInitializer: cargando datos de prueba...");

        pagoRepository.save(new Pago(null, 1L, 15000.0, "TARJETA_CREDITO", "APROBADO", LocalDateTime.now().minusDays(1)));
        pagoRepository.save(new Pago(null, 2L, 8500.0, "DEBITO", "APROBADO", LocalDateTime.now().minusHours(2)));
        pagoRepository.save(new Pago(null, 3L, 25000.0, "EFECTIVO", "PENDIENTE", LocalDateTime.now()));

        logger.info("DataInitializer: {} pagos cargados exitosamente.",
                pagoRepository.count());
    }
}