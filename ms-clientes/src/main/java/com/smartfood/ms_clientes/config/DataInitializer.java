package com.smartfood.ms_clientes.config;

import com.smartfood.ms_clientes.model.Cliente;
import com.smartfood.ms_clientes.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final ClienteRepository clienteRepository;

    public DataInitializer(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() > 0) {
            logger.info("DataInitializer: BD ya tiene {} cliente(s). Omitiendo carga inicial.",
                    clienteRepository.count());
            return;
        }

        logger.info("DataInitializer: cargando datos de prueba...");

        clienteRepository.save(new Cliente(null, "Ana Morales", "ana.m@email.com", "+56912345678", "Av. Siempre Viva 123", LocalDateTime.now().minusDays(10)));
        clienteRepository.save(new Cliente(null, "Roberto Gomez", "roberto.g@email.com", "+56987654321", "Calle Falsa 456", LocalDateTime.now().minusDays(5)));
        clienteRepository.save(new Cliente(null, "Lucia Perez", "lucia.p@email.com", "+56955554444", "Paseo Ahumada 789", LocalDateTime.now()));

        logger.info("DataInitializer: {} clientes cargados exitosamente.",
                clienteRepository.count());
    }
}