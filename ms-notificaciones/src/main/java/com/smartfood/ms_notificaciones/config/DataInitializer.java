package com.smartfood.ms_notificaciones.config;

import com.smartfood.ms_notificaciones.model.Notificacion;
import com.smartfood.ms_notificaciones.repository.NotificacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final NotificacionRepository repository;

    public DataInitializer(NotificacionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Notificacion(null, 1L, "Tu pedido ha sido recibido", LocalDateTime.now()));
            repository.save(new Notificacion(null, 2L, "Tu pago fue procesado con exito", LocalDateTime.now()));
        }
    }
}